package com.example.myapplication.data.firebase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.Warehouse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthClient @Inject constructor(){

    private val auth = Firebase.auth
    private val _event: MutableSharedFlow<AuthEvent> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val event: SharedFlow<AuthEvent> = _event


    fun getUserType(userId: String): Flow<Result<String>> = flow {
        try {
            val document = FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .await()
            val userType = document.getString("role") ?: "unknown"
            emit(Result.success(userType))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }



    suspend fun createAccount(email: String, password: String, role: String, name: String, phoneNumber: String, address: String, imageUrl: String) {
        // Run the Firebase logic inside a suspend coroutine to avoid blocking the main thread
        suspendCoroutine<Unit> { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        val userMap = hashMapOf(
                            "email" to email,
                            "role" to role,
                            "name" to name,
                            "phoneNumber" to phoneNumber,
                            "address" to address,
                            "imageUrl" to imageUrl
                        )

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                cont.resume(Unit)  // Account created successfully
                            }
                            .addOnFailureListener { exception ->
                                cont.resumeWithException(exception)  // Handle failure
                            }
                    } else {
                        cont.resumeWithException(task.exception ?: Exception("Unknown error"))
                    }
                }
        }
    }


    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }



    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


    fun reauthenticateUser(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(Unit))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Reauthentication failed")))
                }
            }
    }


    fun updateUserPassword(newPassword: String, callback: (Result<Unit>) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(Unit))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Password update failed")))
                }
            }
    }






    fun signInWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        try {
            val authResult = suspendCoroutine<AuthResult> { continuation ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(task.result!!)
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception())
                        }
                    }
            }
            // Emit success if sign-in is successful
            _event.tryEmit(AuthEvent.SignedIn(authResult.user?.uid ?: "", email))
            emit(Result.success(Unit))
        } catch (exception: Exception) {
            // Emit failure if an exception occurs
            _event.tryEmit(AuthEvent.SignInUnsuccessful(exception))
            emit(Result.failure(exception))
        }
    }


    fun signOut() {
        auth.signOut()
        _event.tryEmit(AuthEvent.SignedOut)
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getWarehouseById(warehouseId: String, onResult: (Warehouse?) -> Unit) {
        val document = FirebaseFirestore.getInstance()
            .collection("warehouses")
            .document(warehouseId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val warehouse = document.toObject(Warehouse::class.java)
                    onResult(warehouse)
                } else {
                    onResult(null) // Handle the case where warehouse doesn't exist
                }
            }
            .addOnFailureListener {
                onResult(null) // Handle any errors
            }
    }


    fun getUserById(userId: String, onResult: (User?) -> Unit) {
        val document = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    onResult(user)
                } else {
                    onResult(null) // Handle the case where warehouse doesn't exist
                }
            }
            .addOnFailureListener {
                onResult(null) // Handle any errors
            }
    }

    fun updateUser(userId: String, updatedUser: User, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        // Convert the User object to a Map, excluding the 'id' field
        val userMap = mapOf(
            "name" to updatedUser.name,
            "role" to updatedUser.role,
            "email" to updatedUser.email,
            "phoneNumber" to updatedUser.phoneNumber,
            "address" to updatedUser.address,
            "imageUrl" to updatedUser.imageUrl
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId) // Use 'userId' as the document ID
            .set(userMap) // Save the map instead of the User object directly
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    }

    fun updateWarehouse(warehouseId: String, updatedWarehouse: Warehouse, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        // Convert the Warehouse object to a Map, excluding the 'id' field
        val warehouseMap = mapOf(
            "name" to updatedWarehouse.name,
            "space" to updatedWarehouse.space,
            "owner" to updatedWarehouse.owner,
            "assignedWorkers" to updatedWarehouse.assignedWorkers
        )

        FirebaseFirestore.getInstance()
            .collection("warehouses")
            .document(warehouseId) // Use 'warehouseId' as the document ID
            .set(warehouseMap) // Save the map instead of the Warehouse object directly
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    }





}
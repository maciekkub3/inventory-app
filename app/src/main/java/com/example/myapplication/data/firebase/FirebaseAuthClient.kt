package com.example.myapplication.data.firebase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.Warehouse
import com.google.firebase.auth.AuthResult
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




   fun createAccount(email: String, password: String, role: String, name: String, phoneNumber: String, address: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val userMap = hashMapOf(
                        "email" to email,
                        "role" to role,
                        "name" to name,
                        "phoneNumber" to phoneNumber,
                        "address" to address
                    )

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(userId)
                        .set(userMap)
                        .addOnSuccessListener {
                            _event.tryEmit(AuthEvent.SignedUp(userId, email))
                        }
                        .addOnFailureListener { exception ->
                            _event.tryEmit(AuthEvent.SignUpUnsuccessful(exception))
                        }

                } else {
                    _event.tryEmit(AuthEvent.SignUpUnsuccessful(task.exception ?: Exception()))
                }
            }
    }

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

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }



    /*fun signInWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _event.tryEmit(AuthEvent.SignedIn(auth.currentUser?.uid ?: "", email))
                } else {
                    _event.tryEmit(AuthEvent.SignInUnsuccessful(task.exception ?: Exception()))
                }
            }
    }*/

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
}
package com.example.myapplication.ui.Screens.UserScreens.ChooseWarehousesUserScreen



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Warehouse
import com.example.myapplication.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseWarehouseUserViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _warehouses = MutableStateFlow<List<Warehouse>>(emptyList())
    val warehouses: StateFlow<List<Warehouse>> = _warehouses

    private val _navigateToMainScreen = MutableSharedFlow<String>() // To emit the warehouseId
    val navigateToMainScreen = _navigateToMainScreen.asSharedFlow()

    private val _signOutEvent = MutableSharedFlow<Boolean>()
    val signOutEvent = _signOutEvent.asSharedFlow()

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchWarehouses()
    }



    fun signOut(navController: NavController) {
        viewModelScope.launch {
            firebaseAuthRepository.signOut()
            _signOutEvent.emit(true) // Emit an event to notify the screen about successful sign-out
            // Navigate to the SignIn screen
            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.OwnerMenu.route) { inclusive = true }
            }
        }
    }



    fun onWarehouseClick(warehouseId: String) {
        viewModelScope.launch {

            // Emit the event to navigate to the main screen
            _navigateToMainScreen.emit(warehouseId)

            val userId = firebaseAuthRepository.getCurrentUserId()
            if (userId != null) {
                val loginData = mapOf(
                    "userId" to userId,
                    "timestamp" to System.currentTimeMillis() // Store timestamp in milliseconds
                )

                val recentLoginsRef = firestore.collection("warehouses")
                    .document(warehouseId)
                    .collection("recentLogins")

                // Add new login entry
                recentLoginsRef.add(loginData)
                    .addOnSuccessListener {
                        println("Login logged successfully for userId: $userId in warehouse: $warehouseId")

                        // Ensure recent logins do not exceed 50
                        recentLoginsRef.orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener { snapshot ->
                                if (snapshot.size() > 50) {
                                    val excessEntries = snapshot.documents.take(snapshot.size() - 50)
                                    excessEntries.forEach { it.reference.delete() }
                                }
                            }
                            .addOnFailureListener { e ->
                                println("Error retrieving recent logins: ${e.message}")
                            }
                    }
                    .addOnFailureListener { e ->
                        println("Error logging login for userId: $userId, error: ${e.message}")
                    }
            }
        }
    }





    fun fetchWarehouses() {
        viewModelScope.launch {
            val currentUserId = firebaseAuthRepository.getCurrentUserId() ?: return@launch

            firebaseAuthRepository.getUserType(currentUserId).collect { result ->
                result.onSuccess { userType ->
                    val query = when (userType) {
                        "Admin" -> firestore.collection("warehouses")
                            .whereEqualTo("owner", currentUserId) // Fetch warehouses where the user is an owner
                        else -> firestore.collection("warehouses")
                            .whereArrayContains("assignedWorkers", currentUserId) // Fetch warehouses where the user is assigned
                    }

                    query.get()
                        .addOnSuccessListener { result ->
                            val warehouseList = result.map { document ->
                                Warehouse(
                                    id = document.id,
                                    name = document.getString("name") ?: "",
                                    space = document.getString("space") ?: ""
                                )
                            }
                            _warehouses.value = warehouseList
                        }
                        .addOnFailureListener {
                            // Handle failure (e.g., log error)
                        }
                }.onFailure {
                    // Handle error retrieving user type
                }
            }
        }
    }


}






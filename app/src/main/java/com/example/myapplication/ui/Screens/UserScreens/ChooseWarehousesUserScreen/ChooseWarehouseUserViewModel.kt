package com.example.myapplication.ui.Screens.UserScreens.ChooseWarehousesUserScreen



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Warehouse
import com.example.myapplication.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
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
            _navigateToMainScreen.emit(warehouseId) // Emit the event
        }
    }



    fun fetchWarehouses() {
        viewModelScope.launch {
            val currentUserId = firebaseAuthRepository.getCurrentUserId() // Get the current user ID from FirebaseAuthClient

            if (currentUserId != null) {
                firestore.collection("warehouses")
                    .whereArrayContains("assignedWorkers", currentUserId) // Query only warehouses where the current user is assigned
                    .get()
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
            }
        }
    }

}






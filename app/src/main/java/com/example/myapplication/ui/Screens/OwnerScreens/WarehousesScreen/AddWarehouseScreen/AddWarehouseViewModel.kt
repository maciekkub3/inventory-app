package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.AddWarehouseScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.AuthEvent
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AddWarehouseViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(AddWarehouseScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()


    fun updateWarehouseName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updateWarehouseSpace(space: String) {
        _state.update { it.copy(space = space) }
    }

    suspend fun addWarehouseToFirestore(warehouseMap: Map<String, Any>) {
        FirebaseFirestore.getInstance()
            .collection("warehouses")
            .add(warehouseMap)
            .await() // Suspend function
    }

    fun createWarehouse(navController: NavController) {
        viewModelScope.launch {
            try {
                val name = state.value.name
                val space = state.value.space

                // Validate input
                if (name.isEmpty() || space.isEmpty()) {
                    _event.trySend(AuthEvent.SignUpUnsuccessful(Exception("Please fill in all fields")))
                } else {
                    val warehouseMap = hashMapOf(
                        "name" to name,
                        "space" to space
                    )

                    // Perform the Firebase operation
                    addWarehouseToFirestore(warehouseMap)

                    // Emit success event
                    _event.trySend(AuthEvent.SignedUp("Warehouse created", name))
                    navController.popBackStack()

                }
            } catch (e: Exception) {
                // Emit failure event for unexpected exceptions
                _event.trySend(AuthEvent.SignUpUnsuccessful(e))
            }
        }
    }






}
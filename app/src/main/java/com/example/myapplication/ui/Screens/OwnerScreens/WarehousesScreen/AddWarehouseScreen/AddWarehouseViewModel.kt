package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.AddWarehouseScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.AuthEvent
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import com.example.myapplication.navigation.Screen
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

    init {
        fetchWorkerUsers()
        fetchAdminUsers()
    }

    private fun fetchWorkerUsers() {
        viewModelScope.launch {
            try {
                val workers = FirebaseFirestore.getInstance()
                    .collection("users")
                    .whereEqualTo("role", "Worker") // Fetch only workers
                    .get()
                    .await()
                    .documents.map { doc ->
                        User(
                            email = doc.getString("email") ?: "",
                            name = doc.getString("name") ?: "",
                            imageUrl = doc.getString("imageUrl") ?: "",
                            id = doc.id
                        )
                    }
                _state.update { it.copy(availableUsers = workers) }
            } catch (e: Exception) {
                // Handle exception (e.g., show error message)
            }
        }
    }

    private fun fetchAdminUsers() {
        viewModelScope.launch {
            try {
                val admins = FirebaseFirestore.getInstance()
                    .collection("users")
                    .whereEqualTo("role", "Admin") // Fetch only admins
                    .get()
                    .await()
                    .documents.map { doc ->
                        User(
                            email = doc.getString("email") ?: "",
                            name = doc.getString("name") ?: "",
                            imageUrl = doc.getString("imageUrl") ?: "",
                            id = doc.id
                        )
                    }
                _state.update { it.copy(adminUsers = admins) } // Make sure to update a separate state for Admins
            } catch (e: Exception) {
                // Handle exception (e.g., show error message)
            }
        }
    }

    fun updateSelectedOwner(owner: User?) {
        _state.update { it.copy(selectedOwner = owner) }
    }


    fun updateSelectedUsers(users: List<User>) {
        _state.update { it.copy(selectedUsers = users) }
    }


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
                val selectedUsers = state.value.selectedUsers.map { it.id }
                val owner = state.value.selectedOwner?.id


                // Validate input
                if (name.isEmpty() || space.isEmpty() || selectedUsers.isEmpty() || owner == null) {
                    _event.trySend(AuthEvent.SignUpUnsuccessful(Exception("Please fill in all fields and select workers")))
                } else {
                    val warehouseMap = hashMapOf(
                        "name" to name,
                        "space" to space,
                        "assignedWorkers" to selectedUsers,
                        "owner" to owner,
                        "availableSpace" to space

                    )

                    // Perform the Firebase operation
                    addWarehouseToFirestore(warehouseMap)

                    // Emit success event
                    _event.trySend(AuthEvent.SignedUp("Warehouse created", name))

                    navController.navigate(Screen.OwnerWarehouseMenu.route) {
                        // Optionally, pop the back stack so that users can't go back to the "Add Warehouse" screen
                        popUpTo(Screen.OwnerAddWarehouse.route) { inclusive = true }
                        popUpTo(Screen.OwnerWarehouseMenu.route) { inclusive = true }

                    }
                }
            } catch (e: Exception) {
                // Emit failure event for unexpected exceptions
                _event.trySend(AuthEvent.SignUpUnsuccessful(e))
            }
        }
    }
}







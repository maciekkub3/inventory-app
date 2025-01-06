package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.Warehouse
import com.example.myapplication.navigation.Screen
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WarehouseViewViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(WarehouseViewScreenState())
    val state: StateFlow<WarehouseViewScreenState> = _state

    init {
        fetchWorkerUsers()
        fetchAdminUsers()

        val warehouseId = savedStateHandle.get<String>("warehouseId")
        warehouseId?.let {
            fetchWarehouseDetails(it)
        }
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

    fun fetchWarehouseDetails(warehouseId: String) {
        // Assuming you have a method that fetches the warehouse data from Firebase
        firebaseAuthRepository.getWarehouseById(warehouseId) { warehouse ->
            if (warehouse != null) {
                // Fetch users (workers and owner) when warehouse details are fetched
                viewModelScope.launch {
                    try {
                        // Fetch the owner user by ID
                        val owner = FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(warehouse.owner) // The owner field holds the user ID
                            .get()
                            .await()
                            .toObject(User::class.java)


                        // Fetch assigned workers by their userIds
                        val assignedWorkers = FirebaseFirestore.getInstance()
                            .collection("users")
                            .whereIn(FieldPath.documentId(), warehouse.assignedWorkers) // Use document ID for the query
                            .get()
                            .await()
                            .documents.map { doc ->
                                User(
                                    email = doc.getString("email") ?: "",
                                    name = doc.getString("name") ?: "",
                                    imageUrl = doc.getString("imageUrl") ?: "",
                                    id = doc.id  // This is the document ID (the user's ID)
                                )
                            }


                        // Update state with the selected owner and assigned workers
                        _state.update {
                            it.copy(
                                name = warehouse.name,
                                space = warehouse.space,
                                selectedOwner = it.adminUsers.first { admin -> owner?.email == admin.email }, // The owner fetched based on user ID
                                selectedUsers = assignedWorkers
                            )
//                            WarehouseViewScreenState(
//                                name = warehouse.name,
//                                space = warehouse.space,
//                                adminUsers = it.adminUsers,
//                                selectedOwner = it.adminUsers.first { admin -> owner?.email == admin.email }, // The owner fetched based on user ID
//                                availableUsers = it.availableUsers, // Retain the available users
//                                selectedUsers = assignedWorkers // Retain selected users
//                            )
                        }


                    } catch (e: Exception) {
                        // Handle exception (e.g., show error message)
                    }
                }
            } else {
                // Handle error or empty state
            }
        }
    }

    fun saveWarehouseDetails(warehouseId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val currentState = _state.value

        // Create a Warehouse object with updated details (excluding 'id')
        val updatedWarehouse = Warehouse(
            id = warehouseId, // Use this only as the document ID, not as a field
            name = currentState.name,
            space = currentState.space,
            owner = currentState.selectedOwner?.id ?: "", // Assuming the selected owner is a User object with an 'id' field
            assignedWorkers = currentState.selectedUsers.map { it.id } // Assuming selectedUsers is a list of User objects
        )

        // Save the updated warehouse to Firestore
        firebaseAuthRepository.updateWarehouse(
            warehouseId = warehouseId, // Use 'warehouseId' as the document ID
            updatedWarehouse = updatedWarehouse,
            onSuccess = { onSuccess() },
            onError = { exception -> onError(exception) }
        )
    }

    fun removeWarehouse(navController: NavController) {
        val warehouseId = savedStateHandle.get<String>("warehouseId")
        warehouseId?.let {
            viewModelScope.launch {
                try {
                    FirebaseFirestore.getInstance()
                        .collection("warehouses")
                        .document(warehouseId)
                        .delete()
                        .await() // Await for the deletion to complete

                    navController.navigate(Screen.OwnerWarehouseMenu.route) {
                        // Optionally, pop the back stack so that users can't go back to the "Add Warehouse" screen
                        popUpTo(route = "warehouseView/{warehouseId}") { inclusive = true }
                        popUpTo(Screen.OwnerWarehouseMenu.route) { inclusive = true }

                    }
                } catch (e: Exception) {
                    // Handle the error (e.g., show error message)
                    Log.e("Warehouse", "Error removing warehouse: ${e.message}")
                }
            }
        }
    }


    fun updateSelectedOwner(owner: User?) {
        _state.update { it.copy(selectedOwner = owner) }
    }
    fun updateSelectedUsers(users: List<User>) {
        _state.update { it.copy(selectedUsers = users) }
    }

    fun updateWarehouseName(newName: String) {
        _state.update { it.copy(name = newName) }
    }

    // Update warehouse space
    fun updateWarehouseSpace(newSpace: String) {
        _state.update { it.copy(space = newSpace) }
    }



}







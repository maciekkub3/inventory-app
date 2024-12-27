package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen


import androidx.lifecycle.ViewModel
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Warehouse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WarehouseViewViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(WarehouseViewScreenState())
    val state: StateFlow<WarehouseViewScreenState> = _state

    // Initialize the state with warehouse details
    fun setWarehouseDetails(warehouse: Warehouse) {
        _state.value = WarehouseViewScreenState(
            name = warehouse.name,
            space = warehouse.space,
            id = warehouse.id
        )
    }

    fun fetchWarehouseDetails(warehouseId: String) {
        // Assuming you have a method that fetches the warehouse data from Firebase
        firebaseAuthRepository.getWarehouseById(warehouseId) { warehouse ->
            if (warehouse != null) {
                _state.update {
                    WarehouseViewScreenState(
                        name = warehouse.name,
                        space = warehouse.space,
                        id = warehouse.id
                    )
                }
            } else {
                // Handle error or empty state
            }
        }
    }

    fun updateWarehouseName(newName: String) {
        _state.update { it.copy(name = newName) }
    }

    // Update warehouse space
    fun updateWarehouseSpace(newSpace: String) {
        _state.update { it.copy(space = newSpace) }
    }

    fun getWarehouseById(warehouseId: String?): Warehouse? {
        // Simulating fetching warehouse details (replace with actual logic)
        return if (warehouseId != null) {
            Warehouse(id = warehouseId, name = "Warehouse $warehouseId", space = "1000m²")
        } else {
            null
        }
    }

}







package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.ChooseWarehouseScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Warehouse
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseWarehouseViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _warehouses = MutableStateFlow<List<Warehouse>>(emptyList())
    val warehouses: StateFlow<List<Warehouse>> = _warehouses

    private val _filteredWarehouses = MutableStateFlow<List<Warehouse>>(emptyList()) // Store filtered warehouses
    val filteredWarehouses: StateFlow<List<Warehouse>> = _filteredWarehouses

    private val _navigateToWarehouseDetails = MutableSharedFlow<String>() // To emit the warehouseId
    val navigateToWarehouseDetails = _navigateToWarehouseDetails.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchWarehouses()
    }





    fun onWarehouseClick(warehouseId: String) {
        viewModelScope.launch {
            _navigateToWarehouseDetails.emit(warehouseId) // Emit the event
        }
    }



    fun fetchWarehouses() {
        firestore.collection("warehouses")
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
                filterWarehouses() // Apply filtering after fetching
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterWarehouses() // Re-filter when query changes
    }

    private fun filterWarehouses() {
        val query = _searchQuery.value.lowercase()
        _filteredWarehouses.value = _warehouses.value.filter { warehouse ->
            warehouse.name.lowercase().contains(query)
        }
    }

}






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

    private val _navigateToWarehouseDetails = MutableSharedFlow<String>() // To emit the warehouseId
    val navigateToWarehouseDetails = _navigateToWarehouseDetails.asSharedFlow()

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
            }
            .addOnFailureListener {
                // Handle failure (e.g., log error)
            }
    }
}






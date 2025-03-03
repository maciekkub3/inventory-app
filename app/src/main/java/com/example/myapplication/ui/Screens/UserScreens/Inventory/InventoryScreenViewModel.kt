package com.example.myapplication.ui.Screens.UserScreens.Inventory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class InventoryScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {


    private val firestore = FirebaseFirestore.getInstance()

    private val _navigateToItemDetails = MutableSharedFlow<String>()
    val navigateToItemDetails = _navigateToItemDetails.asSharedFlow()

    private val _products = MutableStateFlow<List<Item>>(emptyList())
    val products: StateFlow<List<Item>> = _products

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredProducts = MutableStateFlow<List<Item>>(emptyList()) // Store filtered users
    val filteredProducts: StateFlow<List<Item>> = _filteredProducts

    private var warehouseId: String? = null

    init {
        warehouseId = savedStateHandle.get<String>("warehouseId")
        fetchProducts(warehouseId)

    }

    fun onItemClick(userId: String) {
        viewModelScope.launch {
            _navigateToItemDetails.emit(userId)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterItems() // Re-filter when query changes
    }

    private fun filterItems() {
        val query = _searchQuery.value.lowercase()
        _filteredProducts.value = _products.value.filter { product ->
            product.name.lowercase().contains(query)
        }
    }


    private fun fetchProducts(warehouseId: String?) {
        if (warehouseId == null) {
            _errorMessage.value = "Warehouse ID is null."
            return
        }

        viewModelScope.launch {
            try {
                // Fetch products from the "products" sub-collection of the specified warehouse
                val querySnapshot = firestore.collection("warehouses")
                    .document(warehouseId)
                    .collection("products")
                    .get()
                    .await()

                // Map the documents to a list of Item objects
                val productList = querySnapshot.documents.mapNotNull { document ->
                    val item = document.toObject(Item::class.java)
                    item?.copy(id = document.id) // Add the document ID to each item
                }

                _products.value = productList
                filterItems()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch products: ${e.message}"
            }
        }
    }
}
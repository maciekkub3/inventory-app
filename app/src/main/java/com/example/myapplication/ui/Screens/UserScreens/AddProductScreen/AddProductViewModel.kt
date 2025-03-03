package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.domain.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _state = MutableStateFlow(AddProductUiState())
    val state: StateFlow<AddProductUiState> = _state

    private var warehouseId: String? = null


    init {
        warehouseId = savedStateHandle.get<String>("warehouseId")
        fetchProducts(warehouseId!!)
    }

    fun fetchProducts(warehouseId: String) {
        viewModelScope.launch {
            try {
                println("Fetching products for warehouseId: $warehouseId") // Debug log
                val querySnapshot = firestore.collection("warehouses")
                    .document(warehouseId)
                    .collection("products")
                    .get()
                    .await()

                // Map Firestore documents to Item objects
                val products = querySnapshot.documents.map { document ->
                    document.toObject(Item::class.java)?.let {
                        Item(
                            name = it.name,
                            description = it.description,
                            price = it.price,
                            quantity = it.quantity,
                            id = document.id,
                            imageUrl = it.imageUrl
                        )
                    } ?: Item()
                }

                val initialProducts = querySnapshot.documents.map { document ->
                    document.toObject(Item::class.java)?.let {
                        Item(
                            name = it.name,
                            description = it.description,
                            price = it.price,
                            quantity = it.quantity,
                            id = document.id,
                            imageUrl = it.imageUrl
                        )
                    } ?: Item()
                }

                // Update the state with the fetched products
                _state.value =
                    _state.value.copy(products = products, initialProducts = initialProducts)
                println("Products fetched: ${products.size}") // Debug log

            } catch (exception: Exception) {
                _state.value =
                    _state.value.copy(errorMessage = "Error fetching products: ${exception.message}")
                println("Error fetching products: ${exception.message}") // Debug log

            }
        }
    }


    fun updateProductQuantity(productId: String, newQuantity: Int) {
        val updatedProducts = _state.value.products.map { product ->
            if (product.id == productId) {
                product.copy(adjustedQuantity = newQuantity) // Use separate field for adjustments
            } else product
        }
        _state.value = _state.value.copy(products = updatedProducts)
    }



    fun applyChanges(navController: NavController) {
        viewModelScope.launch {
            _state.value.products.forEach { product ->
                val initialProduct = _state.value.initialProducts.find { it.id == product.id }
                val adjustedQuantity = product.adjustedQuantity // This stores user changes

                if (adjustedQuantity != 0) { // Only process if changes were made
                    val docRef = firestore.collection("warehouses")
                        .document(warehouseId!!)
                        .collection("products")
                        .document(product.id)

                    val newQuantity = (initialProduct!!.quantity + adjustedQuantity).coerceAtLeast(0)

                    // Update Firestore product quantity and lastRestock value
                    docRef.update(
                        mapOf(
                            "quantity" to newQuantity,
                            "lastRestock" to adjustedQuantity // Track the last change made
                        )
                    ).await()

                    // Only add to history if an actual change occurred
                    if (adjustedQuantity != 0) {
                        val historyCollection = firestore.collection("warehouses")
                            .document(warehouseId!!)
                            .collection("history")

                        val historyEntry = mapOf(
                            "itemName" to product.name,
                            "quantityChange" to adjustedQuantity, // Can be positive or negative
                            "timestamp" to System.currentTimeMillis()
                        )

                        historyCollection.add(historyEntry).await()

                        // Ensure history does not exceed 100 entries
                        val querySnapshot = historyCollection
                            .orderBy("timestamp", Query.Direction.ASCENDING) // Oldest first
                            .get()
                            .await()

                        if (querySnapshot.size() > 100) {
                            val entriesToDelete = querySnapshot.documents.take(querySnapshot.size() - 100)
                            entriesToDelete.forEach { document ->
                                historyCollection.document(document.id).delete().await()
                            }
                        }
                    }
                }
            }

            // Navigate back to the main page
            navController.navigate("mainPage/$warehouseId") {
                popUpTo("AddProduct/$warehouseId") { inclusive = true }
                popUpTo("mainPage/$warehouseId") { inclusive = true }
            }
        }
    }



}
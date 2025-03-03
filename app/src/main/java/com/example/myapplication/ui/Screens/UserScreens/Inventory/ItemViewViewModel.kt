package com.example.myapplication.ui.Screens.UserScreens.Inventory


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.Item
import com.example.myapplication.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ItemViewViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()


    private var itemId: String? = null
    private var warehouseId: String? = null

    private val _item = MutableStateFlow<Item?>(null)
    val item: StateFlow<Item?> get() = _item





    init {
        itemId = savedStateHandle.get<String>("itemId")
        warehouseId = savedStateHandle.get<String>("warehouseId")
        fetchItemDetails()
    }



    private fun fetchItemDetails() {
        itemId?.let { id ->
            warehouseId?.let { warehouse ->
                viewModelScope.launch {
                    try {
                        // Fetch the item from the Firestore collection
                        val document = firestore.collection("warehouses")
                            .document(warehouse)
                            .collection("products")
                            .document(id)  // Fetch the item by ID in the "products" sub-collection
                            .get()
                            .await()

                        if (document.exists()) {
                            // Convert the Firestore document to an Item object
                            val itemDetails = document.toObject(Item::class.java)
                            _item.value = itemDetails
                        } else {
                            _item.value = null // Item not found
                        }
                    } catch (e: Exception) {
                        _item.value = null // Handle error if necessary
                    }
                }
            }
        }
    }

    fun deleteItem(navController: NavController) {
        itemId?.let { id ->
            warehouseId?.let { warehouse ->
                viewModelScope.launch {
                    try {
                        val productRef = firestore.collection("warehouses")
                            .document(warehouse)
                            .collection("products")
                            .document(id)

                        val productDoc = productRef.get().await()

                        if (productDoc.exists()) {
                            val itemName = productDoc.getString("name") ?: "Unknown Item"
                            val itemQuantity = productDoc.getLong("quantity")?.toInt() ?: 0

                            if (itemQuantity > 0) {
                                // Add a history entry before deleting the item
                                val historyCollection = firestore.collection("warehouses")
                                    .document(warehouse)
                                    .collection("history")

                                historyCollection.add(
                                    mapOf(
                                        "itemName" to itemName,
                                        "quantityChange" to -itemQuantity, // Negative to show removal
                                        "timestamp" to System.currentTimeMillis()
                                    )
                                ).await()
                            }

                            // Now delete the item from Firestore
                            productRef.delete().await()

                            // Navigate back to inventory screen
                            navController.navigate("Inventory/$warehouseId") {
                                popUpTo("${Screen.ItemView.route}/$itemId/$warehouseId") { inclusive = true }
                                popUpTo("Inventory/$warehouseId") { inclusive = true }
                            }
                        }
                    } catch (e: Exception) {
                        // Handle any errors, e.g., log them or show a toast
                    }
                }
            }
        }
    }



    fun updateQuantity(quantity: Int, remove: Boolean, navController: NavController) {
        val currentQuantity = _item.value?.quantity ?: return
        val newQuantity = if (remove) {
            (currentQuantity - quantity).coerceAtLeast(0) // Prevent negative quantity
        } else {
            (currentQuantity + quantity).coerceAtLeast(0)
        }

        // Calculate the quantity being added or removed
        val restockQuantity = if (remove) -quantity else quantity

        itemId?.let { id ->
            warehouseId?.let { warehouse ->
                viewModelScope.launch {
                    try {
                        val productRef = firestore.collection("warehouses")
                            .document(warehouse)
                            .collection("products")
                            .document(id)

                        val productDoc = productRef.get().await()
                        val itemName = productDoc.getString("name") ?: "Unknown Item"


                        // Update the quantity and lastRestock field
                        productRef.update(
                            mapOf(
                                "quantity" to newQuantity,
                                "lastRestock" to restockQuantity
                            )
                        ).await()

                        // Add to the history sub-collection
                        val historyCollection = firestore.collection("warehouses")
                            .document(warehouse)
                            .collection("history")

                        historyCollection.add(
                            mapOf(
                                "itemName" to itemName,
                                "quantityChange" to restockQuantity,
                                "timestamp" to System.currentTimeMillis()
                            )
                        ).await()

                        // Ensure history does not exceed 20 entries
                        val querySnapshot = historyCollection
                            .orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .await()

                        if (querySnapshot.size() > 100) {
                            val entriesToDelete = querySnapshot.documents.take(querySnapshot.size() - 100)
                            entriesToDelete.forEach { document ->
                                historyCollection.document(document.id).delete().await()
                            }
                        }

                        val previousScreen = navController.previousBackStackEntry?.destination?.route

                        if (previousScreen?.contains("mainPage") == true) {
                            navController.popBackStack()
                        } else {
                            // Navigate back to the inventory screen
                            navController.navigate("Inventory/$warehouseId") {
                                popUpTo("${Screen.ItemView.route}/{itemId}/{warehouseId}") { inclusive = true }
                                popUpTo("Inventory/$warehouseId") { inclusive = true }
                            }
                        }


                    } catch (e: Exception) {
                        // Handle any errors (e.g., log them or show a toast)
                    }
                }
            }
        }
    }
}

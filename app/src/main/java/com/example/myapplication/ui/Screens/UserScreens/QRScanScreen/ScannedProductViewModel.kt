import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ScannedProductViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _itemName = MutableStateFlow("Loading...")
    val itemName: StateFlow<String> = _itemName

    private val _currentQuantity = MutableStateFlow<Int?>(null)
    val currentQuantity: StateFlow<Int?> = _currentQuantity

    private val _scannedQuantity = MutableStateFlow(0)
    val scannedQuantity: StateFlow<Int> = _scannedQuantity

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private var itemId: String? = null
    private var warehouseId: String? = null

    fun processScannedData(scannedValue: String, warehouseId: String) {
        this.warehouseId = warehouseId
        val parts = scannedValue.split(",")

        if (parts.size == 2) {
            itemId = parts[0]
            _scannedQuantity.value = parts[1].toIntOrNull() ?: 0

            itemId?.let { fetchCurrentStock() }
        } else {
            _itemName.value = "Invalid QR Code"
        }
    }

    fun fetchCurrentStock() {
        warehouseId?.let { warehouse ->
            itemId?.let { productId ->
                firestore.collection("warehouses").document(warehouse)
                    .collection("products").document(productId).get()
                    .addOnSuccessListener { document ->
                        _currentQuantity.value = document.getLong("quantity")?.toInt() ?: 0
                        _itemName.value = document.getString("name") ?: "Unknown Item"
                        _imageUrl.value = document.getString("imageUrl")
                    }
                    .addOnFailureListener {
                        _currentQuantity.value = null
                        _itemName.value = "Error loading item"
                        _imageUrl.value = null
                    }
            }
        }
    }

    fun updateQuantity(quantity: Int, remove: Boolean, navController: NavController) {
        val currentQuantity = _currentQuantity.value ?: return
        val newQuantity = if (remove) {
            (currentQuantity - quantity).coerceAtLeast(0)
        } else {
            (currentQuantity + quantity).coerceAtLeast(0)
        }

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

                        // Update the product quantity and last restock
                        productRef.update(
                            mapOf(
                                "quantity" to newQuantity,
                                "lastRestock" to restockQuantity
                            )
                        ).await()

                        // Add the change to the history collection
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

                        // Ensure history does not exceed 100 entries
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



                        // Refresh stock after update
                        // ✅ Navigate back to Main Page after update
                        navController.navigate("mainPage/$warehouse") {
                            popUpTo("scannedProduct/$warehouse") { inclusive = true }
                            popUpTo("qrScanner/$warehouse") { inclusive = true }
                            popUpTo("mainPage/$warehouse") { inclusive = true }
                        }

                    } catch (e: Exception) {
                        // Handle errors if needed
                    }
                }
            }
        }
    }
}

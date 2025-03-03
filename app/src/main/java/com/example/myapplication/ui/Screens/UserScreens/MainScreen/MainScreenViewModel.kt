package com.example.myapplication.ui.Screens.UserScreens.MainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.History
import com.example.myapplication.domain.model.Item
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {


    private var warehouseId: String? = null

    val lastFiveItems = mutableStateOf<List<Item>>(emptyList())

    private val _warehouseCapacityPercentage = mutableStateOf(0)
    val warehouseCapacityPercentage: Int
        get() = _warehouseCapacityPercentage.value

    private val _totalProductsSize = mutableStateOf(0)
    val totalProductsSize: Int
        get() = _totalProductsSize.value

    private val _warehouseSize = mutableStateOf(0)
    val warehouseSize: Int
        get() = _warehouseSize.value


    private val _userType = MutableStateFlow<Result<String>?>(null)
    val userType: StateFlow<Result<String>?> = _userType


    init {
        warehouseId = savedStateHandle.get<String>("warehouseId")
        fetchUserType()
    }

    private fun fetchUserType() {
        viewModelScope.launch {
            val currentUserId = firebaseAuthRepository.getCurrentUserId() ?: return@launch

            firebaseAuthRepository.getUserType(currentUserId).collect { result ->
                _userType.value = result
            }
        }
    }





    suspend fun refresh() {
        fetchLastFiveItems()
        calculateWarehouseCapacityPercentage()
    }

    private fun fetchLastFiveItems() {
        warehouseId?.let { id ->
            // Fetch last 5 history items
            Firebase.firestore.collection("warehouses")
                .document(id)
                .collection("history")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener { historySnapshot ->
                    val historyItems = historySnapshot.toObjects(History::class.java)

                    // Fetch products to match itemName
                    fetchProductsForHistory(historyItems)
                }
        }
    }

    private fun fetchProductsForHistory(historyItems: List<History>) {
        warehouseId?.let { id ->
            Firebase.firestore.collection("warehouses")
                .document(id)
                .collection("products")
                .get()
                .addOnSuccessListener { productsSnapshot ->
                    val products = productsSnapshot.documents.map { document ->
                        // Create an Item object, and set the 'id' field to the document id
                        val item = document.toObject(Item::class.java)
                        item?.copy(id = document.id)  // Set the 'id' field to document.id
                    }.filterNotNull() // Filter out any null values

                    // Match products with history items
                    val matchedItems = historyItems.mapNotNull { history ->
                        products.find { it.name == history.itemName }?.copy(
                            lastRestock = history.quantityChange.toInt()
                        )
                    }

                    lastFiveItems.value = matchedItems
                }
        }
    }


    private suspend fun getWarehouseSize(): Int {
        var size = 0
        warehouseId?.let { id ->
            try {
                // Use await to make sure we get the result before continuing
                val warehouseDoc = Firebase.firestore.collection("warehouses")
                    .document(id)
                    .get()
                    .await()

                // Set size after fetching the data
                size = warehouseDoc.get("space").toString().toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return size
    }

    suspend fun calculateWarehouseCapacityPercentage() {
        warehouseId?.let { id ->
            // Fetch the products
            val productsSnapshot = Firebase.firestore.collection("warehouses")
                .document(id)
                .collection("products")
                .get()
                .await()  // Using await() for suspend function call

            val products = productsSnapshot.documents.map { document ->
                val item = document.toObject(Item::class.java)
                item?.copy(id = document.id)  // Set the 'id' field to document.id
            }.filterNotNull()

            // Calculate the total size of all products
            val totalSize = products.sumOf { it.size!! * it.quantity!! }

            // Get the warehouse size using the suspend function
            val warehouseSize = getWarehouseSize()

            // Update state variables
            _totalProductsSize.value = totalSize.toInt()
            _warehouseSize.value = warehouseSize

            // Calculate the percentage
            val percentage = (totalSize.toDouble() / warehouseSize.toDouble()) * 100

            // Update the capacity percentage
            _warehouseCapacityPercentage.value = percentage.toInt()
        }
    }
}
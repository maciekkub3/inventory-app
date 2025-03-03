package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.AuthEvent
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
class AddNewProductViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _state = MutableStateFlow(AddNewProductUiState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()

    private var warehouseId: String? = null

    init {
        warehouseId = savedStateHandle.get<String>("warehouseId")
    }

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updatePrice(price: String) {
        _state.update { it.copy(price = price) }
    }

    fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun updateImageUri(imageUri: Uri) {
        _state.update { it.copy(imageUri = imageUri) }
    }
    fun updateSize(size: String) {
        _state.update { it.copy(size = size) }
    }

    // Upload image to Firebase Storage
    private suspend fun uploadImageToFirebase(imageUri: Uri): String {
        val storageReference = FirebaseStorage.getInstance().reference
        val fileName = "products/${System.currentTimeMillis()}.jpg"
        val uploadTask = storageReference.child(fileName).putFile(imageUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }

    // Function to add a new product
    fun addProduct(navController: NavController) {
        viewModelScope.launch {
            val currentState = _state.value

            if (warehouseId == null) {
                _state.update { it.copy(errorMessage = "Warehouse ID not found.") }
                return@launch
            }

            if (currentState.name.isEmpty() || currentState.size.isEmpty() || currentState.price.isEmpty() || currentState.description.isEmpty() || currentState.imageUri == null) {
                _state.update { it.copy(errorMessage = "All fields are required.") }
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            try {
                // Check if a product with the same name already exists
                val querySnapshot = firestore.collection("warehouses")
                    .document(warehouseId!!)
                    .collection("products")
                    .whereEqualTo("name", currentState.name)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    // Product with the same name already exists
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "A product with this name already exists.")
                    }
                } else {
                    // Upload image to Firebase Storage
                    val imageUrl = uploadImageToFirebase(currentState.imageUri!!)

                    // Add new product to Firestore
                    val product = hashMapOf(
                        "name" to currentState.name,
                        "price" to currentState.price.toDoubleOrNull(),
                        "description" to currentState.description,
                        "imageUrl" to imageUrl,
                        "quantity" to 0,
                        "size" to currentState.size.toDoubleOrNull()
                    )

                    firestore.collection("warehouses")
                        .document(warehouseId!!)
                        .collection("products")
                        .add(product)
                        .await()

                    _event.trySend(AuthEvent.SignedUp("Product added successfully", currentState.name))
                    // Navigate after success
                    navController.navigate("AddProduct/$warehouseId") {
                        popUpTo("AddNewProduct/$warehouseId") { inclusive = true }
                        popUpTo("AddProduct/$warehouseId") { inclusive = true }
                    }
                }
            } catch (exception: Exception) {
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Error adding product: ${exception.message}")
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}

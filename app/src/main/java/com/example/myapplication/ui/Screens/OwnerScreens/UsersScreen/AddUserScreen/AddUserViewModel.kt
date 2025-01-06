package com.example.myapplication.ui.signin

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.AuthEvent
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.AddUserScreen.AddUserScreenState
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
class AddUserViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(AddUserScreenState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()


    fun updateUserEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun updateRole(role: String) {
        _state.update { it.copy(role = role) }

    }

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }

    }
    fun updatePhoneNumber(phoneNumber: String) {
        _state.update { it.copy(phoneNumber = phoneNumber) }
    }
    fun updateAdress(Adress: String) {
        _state.update { it.copy(Adress = Adress) }
    }
    fun updateImageUri(imageUri: Uri) {
        _state.update { it.copy(imageUri = imageUri) }
    }

    // Upload image to Firebase Storage
    suspend fun uploadImageToFirebase(imageUri: Uri): String {
        val storageReference = FirebaseStorage.getInstance().reference
        val fileName = "users/${System.currentTimeMillis()}.jpg"
        val uploadTask = storageReference.child(fileName).putFile(imageUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }



    // Function to handle user account creation
    fun createAccount(navController: NavController) {
        viewModelScope.launch {
            try {
                // Get the values from state
                val email = state.value.email
                val password = state.value.password
                val role = state.value.role
                val name = state.value.name
                val phoneNumber = state.value.phoneNumber
                val address = state.value.Adress
                val imageUri = state.value.imageUri

                // Validate input
                if (email.isEmpty() || password.isEmpty() || role.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || imageUri == null) {
                    _event.trySend(AuthEvent.SignUpUnsuccessful(Exception("Please fill in all fields, including an image")))
                } else {
                    // Upload image and get the image URL
                    val imageUrl = uploadImageToFirebase(imageUri)

                    // Prepare the data to create the account
                    val userMap = hashMapOf(
                        "email" to email,
                        "password" to password,
                        "role" to role,
                        "name" to name,
                        "phoneNumber" to phoneNumber,
                        "address" to address,
                        "imageUrl" to imageUrl
                    )

                    // Call the repository function to create the account
                    firebaseAuthRepository.createAccount(email, password, role, name, phoneNumber, address, imageUrl)

                    // Emit success event
                    _event.trySend(AuthEvent.SignedUp("User created", email))

                    // Navigate after success
                    navController.navigate(Screen.OwnerUsersView.route) {
                        // Optionally, pop the back stack so that users can't go back to the "Add User" screen
                        popUpTo(Screen.OwnerAddUsers.route) { inclusive = true }
                        popUpTo(Screen.OwnerUsersView.route) { inclusive = true }
                    }
                }
            } catch (exception: Exception) {
                // Emit failure event for unexpected exceptions
                _event.trySend(AuthEvent.SignUpUnsuccessful(exception))
            }
        }
    }

}
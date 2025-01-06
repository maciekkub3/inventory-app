package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen




import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import com.example.myapplication.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = MutableStateFlow(UserViewScreenState())
    val state: StateFlow<UserViewScreenState> = _state

    init {
        val userId = savedStateHandle.get<String>("userId")
        userId?.let {
            fetchUserDetails(it)
        }
    }

    // Initialize the state with warehouse details
    fun setUserDetails(user: User) {
        _state.value = UserViewScreenState(
            name = user.name,
            role = user.role,
            email = user.email,
            phoneNumber = user.phoneNumber,
            address = user.address,
            imageUri = user.imageUrl.let { Uri.parse(it) } // Convert String to Uri
        )
    }

    fun fetchUserDetails(userId: String) {
        // Assuming you have a method that fetches the warehouse data from Firebase
        firebaseAuthRepository.getUserById(userId) { user ->
            if (user != null) {
                _state.update {
                    UserViewScreenState(
                        name = user.name,
                        role = user.role,
                        email = user.email,
                        phoneNumber = user.phoneNumber,
                        address = user.address,
                        imageUri = user.imageUrl.let { Uri.parse(it) } // Convert String to Uri

                    )
                }
            } else {
                // Handle error or empty state
            }
        }
    }

    fun saveUserDetails(userId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val currentState = _state.value

        // Create a User object with updated details (excluding 'id')
        val updatedUser = currentState.imageUri?.toString()?.let {
            User(
                id = userId, // Use this only as the document ID, not as a field
                name = currentState.name,
                role = currentState.role,
                email = currentState.email,
                phoneNumber = currentState.phoneNumber,
                address = currentState.address,
                imageUrl = it
            )
        }

        // Save the updated user to Firestore
        if (updatedUser != null) {
            firebaseAuthRepository.updateUser(
                userId = userId, // Use 'userId' as the document ID
                updatedUser = updatedUser,
                onSuccess = { onSuccess() },
                onError = { exception -> onError(exception) }
            )
        }
    }

    fun removeUser(navController: NavController) {
        val userId = savedStateHandle.get<String>("userId")
        userId?.let {
            viewModelScope.launch {
                try {
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(userId)
                        .delete()
                        .await() // Await for the deletion to complete

                    navController.navigate(Screen.OwnerUsersView.route) {
                        // Optionally, pop the back stack so that users can't go back to the "Add Warehouse" screen
                        popUpTo(route = "userView/{userId}") { inclusive = true }
                        popUpTo(Screen.OwnerUsersView.route) { inclusive = true }

                    }
                } catch (e: Exception) {
                    // Handle the error (e.g., show error message)
                    Log.e("User", "Error removing user: ${e.message}")
                }
            }
        }

    }




    fun updateUserName(newName: String) {
        _state.update { it.copy(name = newName) }
    }
    fun updateUserRole(newRole: String) {
        _state.update { it.copy(role = newRole) }
    }
    fun updateUserEmail(newEmail: String) {
        _state.update { it.copy(email = newEmail) }
    }
    fun updateUserPhoneNumber(newPhoneNumber: String) {
        _state.update { it.copy(phoneNumber = newPhoneNumber) }
    }
    fun updateUserAddress(newAddress: String) {
        _state.update { it.copy(address = newAddress) }
    }
    fun updateUserPassword(newPassword: String) {
        _state.update { it.copy(password = newPassword) }
    }
    fun updateUserImage(newImageUri: Uri) {
        _state.update { it.copy(imageUri = newImageUri) }
    }





}







package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileScreenState())
    val state: StateFlow<EditProfileScreenState> = _state

    val userId = firebaseAuthRepository.getCurrentUserId()

    init{
        fetchUserDetails(userId!!)
    }

    private fun fetchUserDetails(userId: String) {
        // Assuming you have a method that fetches the warehouse data from Firebase
        firebaseAuthRepository.getUserById(userId) { user ->
            if (user != null) {
                _state.update {
                    EditProfileScreenState(
                        name = user.name,
                        email = user.email,
                        phoneNumber = user.phoneNumber,
                        address = user.address,
                        imageUrl = user.imageUrl,
                        role = user.role
                    )
                }
            } else {
                // Handle error or empty state
            }
        }
    }

    fun saveUserDetails(userId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val currentState = _state.value
        val updatedUser = User(
            id = userId,
            name = currentState.name,
            phoneNumber = currentState.phoneNumber,
            address = currentState.address,
            email = currentState.email,
            imageUrl = currentState.imageUrl,
            role = currentState.role
        )

        firebaseAuthRepository.updateUser(
            userId = userId, // Use 'userId' as the document ID
            updatedUser = updatedUser,
            onSuccess = { onSuccess() },
            onError = { exception -> onError(exception) }
        )
    }



    fun updateUserName(newName: String) {
        _state.update { it.copy(name = newName) }
    }

    fun updateUserPhoneNumber(newPhoneNumber: String) {
        _state.update { it.copy(phoneNumber = newPhoneNumber) }
    }
    fun updateUserAddress(newAddress: String) {
        _state.update { it.copy(address = newAddress) }
    }

}
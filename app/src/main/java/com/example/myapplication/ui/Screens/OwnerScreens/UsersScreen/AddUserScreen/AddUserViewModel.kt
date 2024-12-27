package com.example.myapplication.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.AuthEvent
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.AddUserScreen.AddUserScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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



    // Function to handle user account creation
    fun createAccount() {
        val email = state.value.email
        val password = state.value.password
        val role = state.value.role
        val name = state.value.name
        val phoneNumber = state.value.phoneNumber
        val address = state.value.Adress

        if (email.isEmpty() || password.isEmpty() || role.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            // Handle validation error: Show message or perform necessary actions
            _event.trySend(AuthEvent.SignUpUnsuccessful(Exception("Please fill in all fields")))
            return
        }

        // Call the createAccount function from your repository or directly from FirebaseAuthClient
        viewModelScope.launch {
            try {
                firebaseAuthRepository.createAccount(email, password, role, name, phoneNumber, address)
                _event.trySend(AuthEvent.SignedUp("User created", email)) // Or handle success event
            } catch (exception: Exception) {
                _event.trySend(AuthEvent.SignUpUnsuccessful(exception)) // Handle failure
            }
        }
    }

}
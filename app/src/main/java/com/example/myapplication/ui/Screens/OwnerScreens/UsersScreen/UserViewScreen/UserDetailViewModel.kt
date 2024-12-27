package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen




import androidx.lifecycle.ViewModel
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(UserViewScreenState())
    val state: StateFlow<UserViewScreenState> = _state

    // Initialize the state with warehouse details
    fun setUserDetails(user: User) {
        _state.value = UserViewScreenState(
            name = user.name,
            role = user.role,
            email = user.email,
            phoneNumber = user.phoneNumber,
            address = user.address,
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
                    )
                }
            } else {
                // Handle error or empty state
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





}







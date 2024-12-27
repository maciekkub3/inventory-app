package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserListScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _navigateToUserDetails = MutableSharedFlow<String>() // To emit the warehouseId
    val navigateToUserDetails = _navigateToUserDetails.asSharedFlow()

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchUsers()
    }



    fun onUserClick(userId: String) {
        viewModelScope.launch {
            _navigateToUserDetails.emit(userId) // Emit the event
        }
    }

    fun fetchUsers() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val usersList = result.map { document ->
                    User(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        address = document.getString("adress") ?: "",
                        phoneNumber = document.getString("phone") ?: "",
                        role = document.getString("role") ?: "",
                        assigned_warehouse = document.get("assigned_warehouse") as? List<String> ?: emptyList(),
                        date_of_employement = document.getString("date_of_employement") ?: "",
                        last_login = document.getString("last_login") ?: ""
                    )
                }
                _users.value = usersList
            }
            .addOnFailureListener {
                // Handle failure (e.g., log error)
            }
    }
}



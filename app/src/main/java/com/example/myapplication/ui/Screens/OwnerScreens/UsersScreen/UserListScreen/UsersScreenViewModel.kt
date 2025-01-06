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

    private val _filteredUsers = MutableStateFlow<List<User>>(emptyList()) // Store filtered users
    val filteredUsers: StateFlow<List<User>> = _filteredUsers

    private val _navigateToUserDetails = MutableSharedFlow<String>()
    val navigateToUserDetails = _navigateToUserDetails.asSharedFlow()

    private val firestore = FirebaseFirestore.getInstance()

    // Store the search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        fetchUsers()
    }

    fun onUserClick(userId: String) {
        viewModelScope.launch {
            _navigateToUserDetails.emit(userId)
        }
    }

    fun fetchUsers() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val usersList = result.mapNotNull { document ->
                    val role = document.getString("role") ?: ""
                    if (role != "Owner") {
                        User(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            email = document.getString("email") ?: "",
                            address = document.getString("adress") ?: "",
                            phoneNumber = document.getString("phone") ?: "",
                            imageUrl = document.getString("imageUrl") ?: "",
                            role = role
                        )
                    } else {
                        null // Exclude "Owner" role users
                    }
                }
                _users.value = usersList
                filterUsers() // Apply filtering after fetching
            }
            .addOnFailureListener {
                // Handle failure
            }
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterUsers() // Re-filter when query changes
    }

    private fun filterUsers() {
        val query = _searchQuery.value.lowercase()
        _filteredUsers.value = _users.value.filter { user ->
            user.name.lowercase().contains(query)
        }
    }
}


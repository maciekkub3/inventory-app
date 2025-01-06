package com.example.myapplication.ui.Screens.OwnerScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.firebase.FirebaseAuthClient
import com.example.myapplication.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerMenuViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthClient,
) : ViewModel() {

    private val _signOutEvent = MutableSharedFlow<Boolean>()
    val signOutEvent = _signOutEvent.asSharedFlow()

    fun signOut(navController: NavController) {
        viewModelScope.launch {
            firebaseAuthRepository.signOut()
            _signOutEvent.emit(true) // Emit an event to notify the screen about successful sign-out
            // Navigate to the SignIn screen
            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.OwnerMenu.route) { inclusive = true }
            }
        }
    }
}

package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    ) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val user = auth.currentUser
        if (user == null) {
            onError("User not authenticated")
            return
        }

        val email = user.email
        if (email == null) {
            onError("User email not found")
            return
        }

        val credential = EmailAuthProvider.getCredential(email, currentPassword)
        viewModelScope.launch {
            try {
                // Reauthenticate the user
                user.reauthenticate(credential).await()
                // Update the password
                user.updatePassword(newPassword).await()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }
}

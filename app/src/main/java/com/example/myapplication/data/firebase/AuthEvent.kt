package com.example.myapplication.data.firebase

sealed interface AuthEvent {
    data class SignedUp(val userId: String, val email: String) : AuthEvent
    data class SignUpUnsuccessful(val exception: Exception) : AuthEvent
    data class SignedIn(val userId: String, val email: String) : AuthEvent
    data class SignInUnsuccessful(val exception: Exception) : AuthEvent
    data object SignedOut : AuthEvent
}
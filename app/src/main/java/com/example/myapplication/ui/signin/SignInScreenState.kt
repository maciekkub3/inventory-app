package com.example.myapplication.ui.signin

data class SignInScreenState(
    val isLoading: Boolean = false,
    val userEmail: String = "",
    val userPassword: String = "",
    val isSignedIn: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)
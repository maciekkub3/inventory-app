package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen

import android.net.Uri

data class UserViewScreenState(
    val name: String = "",
    val password: String = "",
    val role: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val dateOfEmployment: String = "",
    val lastLogin: String = "",
    val imageUri: Uri? = null,
    val originalImageUrl: String = ""


)
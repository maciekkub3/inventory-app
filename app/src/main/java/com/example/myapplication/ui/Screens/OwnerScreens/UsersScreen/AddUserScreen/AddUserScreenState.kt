package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.AddUserScreen

import android.net.Uri

data class AddUserScreenState(
    val name: String = "",
    val role: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val Adress: String = "",
    val imageUri: Uri? = null

)
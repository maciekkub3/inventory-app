package com.example.myapplication.ui.Screens.UserScreens.WorkersScreen

import android.net.Uri

data class WorkerViewScreenState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val imageUri: Uri? = null
)
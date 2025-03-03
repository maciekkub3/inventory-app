package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import android.net.Uri

data class AddNewProductUiState (
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUri: Uri? = null,
    val size: String = ""

)

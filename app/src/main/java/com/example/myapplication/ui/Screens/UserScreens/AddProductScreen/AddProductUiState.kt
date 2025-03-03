package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import com.example.myapplication.domain.model.Item

data class AddProductUiState(
    val products: List<Item> = emptyList(),
    val errorMessage: String? = null,
    val initialProducts: List<Item> = emptyList()

)
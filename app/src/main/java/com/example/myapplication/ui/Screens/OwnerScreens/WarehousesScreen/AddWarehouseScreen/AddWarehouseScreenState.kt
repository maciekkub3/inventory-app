package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.AddWarehouseScreen

import com.example.myapplication.domain.model.User

data class AddWarehouseScreenState (
    val name: String = "",
    val space: String = "",
    val availableUsers: List<User> = emptyList(),
    val selectedUsers: List<User> = emptyList(),
    val adminUsers: List<User> = emptyList(),
    val selectedOwner: User? = null
)


package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen

import com.example.myapplication.domain.model.User

data class WarehouseViewScreenState (
    val name: String = "",
    val space: String = "",
    val availableUsers: List<User> = emptyList(),
    val selectedUsers: List<User> = emptyList(),
    val adminUsers: List<User> = emptyList(),
    val selectedOwner: User? = null

)
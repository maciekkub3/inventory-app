package com.example.myapplication.domain.model

data class Warehouse(
    val assignedWorkers: List<User>,
    val inventory: List<Item>,
    val name: String,
    val space: Int
)

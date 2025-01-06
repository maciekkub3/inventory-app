package com.example.myapplication.domain.model

import com.google.firebase.firestore.PropertyName


data class Warehouse(
    @PropertyName("id") val id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("space") val space: String = "",
    @PropertyName("assignedWorkers") val assignedWorkers: List<String> = listOf(), // List of user IDs for workers
    @PropertyName("owner") val owner: String = "" // User ID of the owner
)

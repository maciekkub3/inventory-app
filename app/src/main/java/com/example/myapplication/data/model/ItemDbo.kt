package com.example.myapplication.data.model

data class ItemDbo(
    val id: String = "",
    val warehouseId: String,
    val description: String,
    val image_id: Int,
    val last_restock_value: Int,
    val name: String,
    val price: Int,
    val quantity: Int
)

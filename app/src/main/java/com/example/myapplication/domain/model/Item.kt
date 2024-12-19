package com.example.myapplication.domain.model

import androidx.annotation.DrawableRes
import java.time.LocalDateTime

data class Item(
    val id: String,
    val warehouseId: String,
    val description: String,
    @DrawableRes val image_id: Int,
    val last_restock_value: Int,
    val name: String,
    val price: Int,
    val quantity: Int
)
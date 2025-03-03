package com.example.myapplication.domain.model

import com.google.firebase.firestore.PropertyName


data class Item(
    @PropertyName("name") val name: String = "",
    @PropertyName("description") val description: String = "",
    @PropertyName("price") val price: Int = 0,
    @PropertyName("quantity") val quantity: Int = 0,
    @PropertyName("imageUrl") val imageUrl: String = "",
    @PropertyName("lastRestock") val lastRestock: Int = 0,
    @PropertyName("id") val id: String = "",
    @PropertyName("size") val size: Double = 0.0,
    @PropertyName ("adjustedQuantity") val adjustedQuantity: Int = 0
)
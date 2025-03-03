package com.example.myapplication.domain.model

import com.google.firebase.firestore.PropertyName

data class History(
    @PropertyName("itemName") val itemName: String = "",
    @PropertyName("quantityChange") val quantityChange: Int = 0,
    @PropertyName("timestamp") val timestamp: Long = 0,
    val date: String = ""
)
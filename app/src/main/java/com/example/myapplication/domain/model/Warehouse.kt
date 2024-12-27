package com.example.myapplication.domain.model

import com.google.firebase.firestore.PropertyName


data class Warehouse(
    @PropertyName("id") val id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("space") val space: String = ""
)
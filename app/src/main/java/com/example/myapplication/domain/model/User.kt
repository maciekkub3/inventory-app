package com.example.myapplication.domain.model

import com.google.firebase.firestore.PropertyName

data class User(
    @PropertyName("name") val name: String = "",
    @PropertyName("email") val email: String = "",
    @PropertyName("address") val address: String = "",
    @PropertyName("phoneNumber") val phoneNumber: String = "",
    @PropertyName("role") val role: String = "",
    @PropertyName("assigned_warehouse") val assigned_warehouse: List<String> = emptyList(),
    @PropertyName("date_of_employement") val date_of_employement: String = "",
    @PropertyName("last_login") val last_login: String = "",
    @PropertyName("id") val id: String = ""
)






/*
"name" to name,
"email" to email,
"adress" to adress,
"phone" to phone,
"role" to role,
 */
package com.example.myapplication.domain.model

data class User(
    val name: String,
    val email: String,
    val address: String,
    val phone: String,
    val role: String,
    val assigned_warehouse: List<String>,
    val date_of_employement: String,
    val last_login: String

)
/*
"name" to name,
"email" to email,
"adress" to adress,
"phone" to phone,
"role" to role,
 */
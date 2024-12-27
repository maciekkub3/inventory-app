package com.example.myapplication.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val CharcoalBlue = Color(0xFF3C4C5E)
val GrayishBlue = Color(0xFF58616D)
val DarkTealBlue = Color(0xFF1D3A47)
val DarkSlateGray = Color(0xFF47505B)
val DarkGrayish = Color(0xFF373C46)
val Dark = Color(0xFF4D4D4D)
val DarkWeathered = Color(0xFF323232)
val backgroundGradientBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF0F2027), // Darker shade at the top
        Color(0xFF203A43),
        Color(0xFF2C5364) // Lighter shade at the bottom
    )
)

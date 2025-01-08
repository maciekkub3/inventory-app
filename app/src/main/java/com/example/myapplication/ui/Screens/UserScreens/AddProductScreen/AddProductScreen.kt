package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.AddProductBottomBar
import com.example.myapplication.ui.common.AddProductLabel
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun AddProductScreen(
    navController: NavController,

    ) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Add Product",
                onBackClick = {navController.popBackStack()}
            )
        },
        bottomBar = {
            AddProductBottomBar(
                firstColor = Color.Red,
                secondColor = Color(0xFF4C9DAF),
                firstText = "Cancel",
                secondText = "Apply",
                onFirstButtonClick = { /* Handle first button click */ },
                onSecondButtonClick = { /* Handle second button click */ },
                AddNewButtonClick = { navController.navigate(Screen.AddNewProduct.route) }
            )
        }
    )
    { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkSlateGray)
        ) {
            //SearchLabel()
            AddProductLabel(
                name = "Coca Cola",
                image = painterResource(id = R.drawable.colka)
            )
            AddProductLabel(
                name = "Black Label",
                image = painterResource(id = R.drawable.label)
            )
            AddProductLabel(
                name = "Drill",
                image = painterResource(id = R.drawable.drill)
            )
            AddProductLabel(
                name = "Air Pods",
                image = painterResource(id = R.drawable.airpods)
            )
            AddProductLabel(
                name = "Plank",
                image = painterResource(id = R.drawable.plank)
            )
        }

    }
}

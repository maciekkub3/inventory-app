package com.example.myapplication.ui.Screens.UserScreens.History

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.HistoryLabel
import com.example.myapplication.ui.theme.DarkSlateGray


@Composable
fun HistoryScreen(
    navController: NavController,
    ) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "History",
                onBackClick = {navController.popBackStack()}
            )
        },

        )
    { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkSlateGray)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            HistoryLabel(
                text = "Coca Cola",
                value = "85",
                date = "20.10.2015",
                isPlus = true
            )
            HistoryLabel(
                text = "Coca Cola",
                value = "20",
                date = "20.10.2015",
                isPlus = false
            )
            HistoryLabel(
                text = "Air Pods",
                value = "85",
                date = "20.10.2015",
                isPlus = true
            )
            HistoryLabel(
                text = "Drill",
                value = "20",
                date = "20.10.2015",
                isPlus = false
            )
            HistoryLabel(
                text = "Black Label",
                value = "105",
                date = "20.10.2015",
                isPlus = false
            )
        }

    }
}
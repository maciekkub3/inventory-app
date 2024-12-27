package com.example.myapplication.ui.Screens.UserScreens.ChooseWarehousesUserScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.domain.model.Warehouse
import com.example.myapplication.ui.common.BottomBarWithText
import com.example.myapplication.ui.common.LogoutTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun ChooseWarehouseUserScreen(

    navController: NavController, // Pass the NavController here
    viewModel: ChooseWarehouseUserViewModel = hiltViewModel(),
) {
    val warehouses by viewModel.warehouses.collectAsState()
    val warehouseCount = warehouses.size





    Scaffold(
        topBar = {
            LogoutTopAppBar(
                title = "Warehouses",
                onBackClick = {navController.navigateUp()}
            )
        },
        bottomBar = {
            BottomBarWithText("Available warehouses: $warehouseCount")
        }
    )
    { innerPadding ->

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray) // Background color
        ) {
            items(warehouses) { warehouse ->
                WarehouseItem(warehouse, onWarehouseClick = {
                    // Navigate to the WarehouseDetailsScreen when clicked
                    viewModel.onWarehouseClick(warehouse.id)
                })
            }
        }

    }
}


@Composable
fun WarehouseItem(warehouse: Warehouse, onWarehouseClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onWarehouseClick() }
        ,

        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrayishBlue)
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = warehouse.name, color = Color.White)
                Text(text = "Space: ${warehouse.space}", color = Color.White)
            }
        }
    }
}
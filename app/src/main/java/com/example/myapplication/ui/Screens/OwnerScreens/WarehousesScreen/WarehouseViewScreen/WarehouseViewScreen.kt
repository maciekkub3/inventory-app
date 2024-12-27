package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.InformationTextField
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun WarehouseDetailsScreen(
    navController: NavController,
    warehouseId: String?,
    viewModel: WarehouseViewViewModel = hiltViewModel() // Get ViewModel via Hilt
) {

    LaunchedEffect(warehouseId) {
        warehouseId?.let {
            viewModel.fetchWarehouseDetails(it) // Fetch warehouse details from Firebase
        }
    }

    val state = viewModel.state.collectAsState().value


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = state.name,
                onBackClick = {navController.navigateUp()}
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)
        ) {

            InformationTextField(
                dataType = "Name",
                userInput = state.name,
                onValueChange = { newName ->
                    viewModel.updateWarehouseName(newName) // Update the name in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display and edit the warehouse space
            InformationTextField(
                dataType = "Space",
                userInput = state.space,
                onValueChange = { newSpace ->
                    viewModel.updateWarehouseSpace(newSpace) // Update the space in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.ChooseWarehouseScreen


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
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.BottomBarWithTextAndButton
import com.example.myapplication.ui.common.SearchLabel
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun ChooseWarehouseScreen(
    navController: NavController,
    viewModel: ChooseWarehouseViewModel = hiltViewModel(),
) {
    val warehouses by viewModel.filteredWarehouses.collectAsState() // Observe filtered warehouses
    val warehouseCount = warehouses.size
    val searchQuery by viewModel.searchQuery.collectAsState() // Observe search query

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Warehouses",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomBarWithTextAndButton("Available warehouses: $warehouseCount", onButtonClick = { navController.navigate(Screen.OwnerAddWarehouse.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchLabel(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    viewModel.onSearchQueryChanged(query)
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkSlateGray)
            ) {
                items(warehouses) { warehouse ->
                    WarehouseItem(warehouse, onWarehouseClick = {
                        viewModel.onWarehouseClick(warehouse.id)
                    })
                }
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
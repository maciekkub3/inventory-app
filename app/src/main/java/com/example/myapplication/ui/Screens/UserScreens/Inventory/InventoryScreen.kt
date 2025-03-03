package com.example.myapplication.ui.Screens.UserScreens.Inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.ItemRow
import com.example.myapplication.ui.common.SearchLabel
import com.example.myapplication.ui.theme.DarkSlateGray


@Composable
fun InventoryScreen(
    navController: NavController,
    warehouseId: String,
    viewModel: InventoryScreenViewModel = hiltViewModel()
    ) {

    val products by viewModel.filteredProducts.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState() // Observe search query


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Inventory",
                onBackClick = { navController.popBackStack() }
            )
        },
        contentWindowInsets = WindowInsets(0.dp),

        ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)

        ) {
            Column() {
                SearchLabel(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query ->
                        viewModel.onSearchQueryChanged(query)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = "Quantity",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center


                    )
                    Text(
                        text = "Item",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center


                        )
                    Text(
                        text = "Last Updates",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center

                    )


                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    items(products) { product ->
                        ItemRow(
                            id = product.id,
                            quantity = product.quantity.toString(),
                            item = product.name,
                            lastRestock = product.lastRestock.toString(),
                            imageUrl = product.imageUrl,
                            price = product.price.toString(),
                            onItemClick = { itemId ->
                                navController.navigate("${Screen.ItemView.route}/$itemId/$warehouseId")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }
    }
}




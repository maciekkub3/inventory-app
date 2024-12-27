package com.example.myapplication.ui.Screens.UserScreens.Inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.Screens.UserScreens.MainScreen.InventoryItemRow
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.SearchLabel
import com.example.myapplication.ui.theme.DarkSlateGray


@Preview
@Composable
fun InventoryScreen(

) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Inventory",
                onBackClick = {}
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
            SearchLabel()
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
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = "Item",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = "Last Restock",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold

                )


            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                data class InventoryItem(
                    val quantity: String,
                    val item: String,
                    val lastRestock: String,
                    val imageResId: Int,
                    val price: String
                )


                val items = listOf(
                    InventoryItem("85", "Coca Cola", "55pcs", R.drawable.colka, "22"),
                    InventoryItem("85", "drill", "55pcs", R.drawable.drill, "42"),
                    InventoryItem("85", "Black label", "55pcs", R.drawable.label, "100"),
                    InventoryItem("85", "Plank", "55pcs", R.drawable.plank, "10"),
                    InventoryItem("85", "Airpods Pro", "55pcs", R.drawable.airpods, "200"),
                    InventoryItem("85", "drill", "55pcs", R.drawable.drill, "42"),
                    InventoryItem("85", "Black label", "55pcs", R.drawable.label, "100"),
                    InventoryItem("85", "Plank", "55pcs", R.drawable.plank, "10"),
                    InventoryItem("85", "Airpods Pro", "55pcs", R.drawable.airpods, "200"),
                )

                items.forEach { item ->
                    InventoryItemRow(
                        item.quantity,
                        item.item,
                        item.lastRestock,
                        item.imageResId,
                        item.price
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}


package com.example.myapplication.ui.Screens.UserScreens.Inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun InventoryScreen(
    navController: NavController,

    ) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Inventory",
                onBackClick = { navController.popBackStack() }
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)

        ) {
            //SearchLabel()
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
                    ItemRow(
                        item.quantity,
                        item.item,
                        item.lastRestock,
                        item.imageResId,
                        item.price,
                        onItemClick = { navController.navigate(Screen.ItemView.route) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}

@Composable
fun ItemRow(
    quantity: String,
    item: String,
    lastRestock: String,
    imageResId: Int,
    price: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp)
            ) // Apply shadow with rounded corners
            .background(
                GrayishBlue,
                RoundedCornerShape(10.dp)
            ) // Background with rounded corners
            .padding(8.dp)
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = quantity,
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "x",
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .offset(y = (-1).dp),
                color = Color.White,
                fontWeight = FontWeight.Light
            )
            Image(
                painter = painterResource(imageResId),
                contentDescription = item,
                Modifier.size(30.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$price$",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = lastRestock,
            color = Color.LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

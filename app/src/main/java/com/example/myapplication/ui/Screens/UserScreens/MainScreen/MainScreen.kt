package com.example.myapplication.ui.Screens.UserScreens.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.common.bottomBorder
import com.example.myapplication.ui.theme.CharcoalBlue
import com.example.myapplication.ui.theme.DarkTealBlue
import com.example.myapplication.ui.theme.GrayishBlue
import com.example.myapplication.ui.theme.backgroundGradientBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = { DashboardTopBar() },
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(backgroundGradientBrush)
        ) {

            Spacer(modifier = Modifier.height(12.dp))


            // Top Cards: Warehouse Capacity & Scan
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                InfoCard(title = "Warehouse Capacity", percentage = 85)
                ScanCard()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add Product Button
            AddProductButton()

            Spacer(modifier = Modifier.height(16.dp))

            // Grid Buttons: History, Workers, Reports
            DashboardGridButtons()

            Spacer(modifier = Modifier.height(16.dp))

            // Inventory Section
            InventorySection()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar() {
    TopAppBar(
        modifier = Modifier.bottomBorder(
            strokeWidth = 1.dp, color = Color.Black
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = { /* Handle menu */ }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Handle settings */ }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F2027))
    )
}

@Composable
fun InfoCard(title: String, percentage: Int) {
    Card(
        modifier = Modifier.size(150.dp),
        colors = CardDefaults.cardColors(containerColor = CharcoalBlue),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF374251), Color(0xFF515B68)),
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
                        end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
        )
        {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                CircularProgressIndicator(
                    progress = percentage / 100f,
                    color = Color.Cyan,
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    text = "$percentage%",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}

@Composable
fun ScanCard() {
    Card(
        modifier = Modifier.size(150.dp),
        colors = CardDefaults.cardColors(containerColor = CharcoalBlue),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF374251), Color(0xFF515B68)),
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
                        end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.icons8_qrcode),
                    contentDescription = "Scan",
                    modifier = Modifier.size(70.dp)
                )
                Text(
                    text = "Scan",
                    color = Color.White,
                    fontSize = 30.sp,

                    )
            }
        }

    }
}

@Composable
fun AddProductButton() {
    Button(
        onClick = { /* Handle Add Product */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CharcoalBlue),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.icons8_wproduct),
            contentDescription = "Add Product",
            Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Add Product", color = Color.White, fontSize = 18.sp)
    }
}

@Composable
fun DashboardGridButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        GridButton("History", painterResource(R.drawable.icons8_history))

        if (true) { //TODO if user is owner

            GridButton("Workers", painterResource(R.drawable.icons8_users))
        }
        GridButton("Raports", painterResource(R.drawable.icons8_file))
    }
}

@Composable
fun GridButton(text: String, painter: Painter) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable { /* Handle button click */ },
        colors = CardDefaults.cardColors(containerColor = DarkTealBlue),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter,
                    contentDescription = text,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Text(text = text, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}


//TODO Pojebane gowno
@Composable
fun UseButtons() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(60.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1D3A47))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icons8_users),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Workers", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}


@Composable
fun InventorySection() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF374251)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CharcoalBlue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.icons8_inventory),
                        contentDescription = "Inventory",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Inventory",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.icons8_arrowforward),
                    contentDescription = "More",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF374251), Color(0xFF515B68)),
                            start = androidx.compose.ui.geometry.Offset(
                                0f,
                                Float.POSITIVE_INFINITY
                            ),
                            end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                        )
                    )
            ) {
                Column() {
                    Text(
                        text = "Recently Added",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 4.dp)
                    )

                    Divider(thickness = 0.7.dp, color = Color.Gray)

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
    }
}


@Composable
fun InventoryItemRow(
    quantity: String,
    item: String,
    lastRestock: String,
    imageResId: Int,
    price: String
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
            .padding(8.dp),
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


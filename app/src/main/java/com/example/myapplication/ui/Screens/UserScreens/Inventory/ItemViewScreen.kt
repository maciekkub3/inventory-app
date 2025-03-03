package com.example.myapplication.ui.Screens.UserScreens.Inventory

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray
@Composable
fun ItemViewScreen(
    warehouseId: String,
    itemId: String,
    navController: NavController,
    viewModel: ItemViewViewModel = hiltViewModel(),
    context: Context = LocalContext.current // Getting context for Toast

) {
    val item by viewModel.item.collectAsState() // Observe item details
    var quantityInput by remember { mutableStateOf("0") } // Track input quantity value
    val currentQuantity = item?.quantity ?: 0 // Get the quantity from Firestore

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Item View",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "REMOVE",
                secondText = "ADD",
                onFirstButtonClick = {
                    // Decrement quantity logic
                    val quantityToRemove = quantityInput.toIntOrNull() ?: 0
                    if (quantityToRemove in 1..currentQuantity) {
                        viewModel.updateQuantity(quantityToRemove, remove = true, navController)
                    } else if (quantityToRemove > currentQuantity) {
                        Toast.makeText(context, "Cannot remove more than the available quantity", Toast.LENGTH_SHORT).show()
                    }
                },
                onSecondButtonClick = {
                    // Increment quantity logic
                    val quantityToAdd = quantityInput.toIntOrNull() ?: 0
                    if (quantityToAdd > 0) {
                        viewModel.updateQuantity(quantityToAdd, remove = false, navController)
                    }
                }
            )
        }
    ) { innerPadding ->



        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            if (item != null) {
                // Show item data when fetched successfully
                Row() {
                    Spacer(modifier = Modifier.weight(1f))
                    DeleteItemButton(viewModel, navController)
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = item!!.name, // Display item name
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = item!!.imageUrl, // Assuming item.imageUrl contains the URL to the image
                        contentDescription = item!!.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                    )
                }


                Spacer(modifier = Modifier.height(30.dp))

                ItemInfo(quantity = item!!.quantity, price = item!!.price, size = item!!.size.toDouble())

                Spacer(modifier = Modifier.height(30.dp))

                ItemDescription(description = item!!.description)

                Spacer(modifier = Modifier.height(40.dp))

                AddOrRemove(
                    quantityInput = quantityInput,
                    onDecrement = {
                        val current = quantityInput.toIntOrNull() ?: 0
                        if (current > 0) {
                            quantityInput = (current - 1).toString()
                        }
                    },
                    onIncrement = {
                        val current = quantityInput.toIntOrNull() ?: 0
                        // Allow adding quantity even if it's 0
                        quantityInput = (current + 1).toString()
                    },
                    onQuantityChange = { newQuantity ->
                            quantityInput = newQuantity.toString()

                    }
                )

                Spacer(modifier = Modifier.height(40.dp))



            } else {

                Text(
                    text = "Loading item details...",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DeleteItemButton(viewModel: ItemViewViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Column {

        Icon(
            painter = painterResource(R.drawable.icons8_remove),
            tint = Color.Red,
            contentDescription = "remove",
            modifier = Modifier
                .size(35.dp)
                .clickable {showDialog = true}
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Confirm Deletion", color = Color.Black) },
                text = { Text("Are you sure you want to delete this item from the database?", color = Color.Black) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteItem(navController) // Call delete function
                            showDialog = false
                        }
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                }
            )
        }
    }
}


@Composable
fun ItemInfo(quantity: Int, price: Int, size: Double) {
    Box(
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF232529))
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Quantity",
                    color = Color.Gray,
                    fontSize = 13.sp,
                )
                Text(
                    text = quantity.toString(),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Price",
                    color = Color.Gray,
                    fontSize = 13.sp,
                )
                Text(
                    text = "$$price",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Size",
                    color = Color.Gray,
                    fontSize = 13.sp,
                )
                Text(
                    text = "${size}m3", // Correctly formatting the double value
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun ItemDescription(description: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF232529))
            .fillMaxWidth()
            .background(DarkGrayish)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(100.dp)
        ) {
            Text(
                text = "Item Description",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Divider(thickness = 1.dp, color = Color(0xFF515455))
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = description,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}

@Composable
fun AddOrRemove(
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    onQuantityChange: (Int) -> Unit,
    quantityInput: String


) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.icons8_minus),
            contentDescription = "minus",
            tint = Color.Red,
            modifier = Modifier
                .size(60.dp)
                .clickable {onDecrement()}
        )

        TextField(
            value = quantityInput, // Bind input value
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                onQuantityChange(newValue) // Update quantity on input change
            },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            ),
            modifier = Modifier
                .width(100.dp)
        )

        Icon(
            painter = painterResource(R.drawable.icons8_plus),
            contentDescription = "plus",
            tint = Color.Green,
            modifier = Modifier
                .size(60.dp)
                .clickable {onIncrement()}
        )
    }
}

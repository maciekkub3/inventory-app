package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.myapplication.domain.model.Item
import com.example.myapplication.ui.common.AddProductBottomBar
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue

@Composable
fun AddProductScreen(
    warehouseId: String,
    navController: NavController,
    viewModel: AddProductViewModel = hiltViewModel()
) {

    val uiState = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Add Product",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            AddProductBottomBar(
                firstColor = Color.Red,
                secondColor = Color(0xFF4C9DAF),
                firstText = "Cancel",
                secondText = "Apply",
                onFirstButtonClick = { navController.popBackStack() },
                onSecondButtonClick = { viewModel.applyChanges(navController) },
                AddNewButtonClick = { navController.navigate("AddNewProduct/$warehouseId") }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkSlateGray)
        ) {
            items(uiState.products) { product ->
                AddProductLabel(
                    product = product,
                    quantity = product.adjustedQuantity, // Show adjustedQuantity instead of stock
                    onIncrement = {
                        val newQuantity = product.adjustedQuantity + 1
                        viewModel.updateProductQuantity(product.id, newQuantity)
                    },
                    onDecrement = {
                        val minQuantity = -product.quantity // Prevent decrementing below -initial stock
                        val newQuantity = (product.adjustedQuantity - 1).coerceAtLeast(minQuantity)
                        viewModel.updateProductQuantity(product.id, newQuantity)
                    },
                    onQuantityChange = { newQuantity ->
                        val minQuantity = -product.quantity // Ensure input doesn't exceed negative limit
                        val validQuantity = newQuantity.coerceAtLeast(minQuantity) // Prevent over-reduction
                        viewModel.updateProductQuantity(product.id, validQuantity)
                    }
                )
            }
        }



    }
}
@Composable
fun AddProductLabel(
    product: Item,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 10.dp)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(GrayishBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.icons8_minus),
                    contentDescription = "minus",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onDecrement()
                        }
                )

                BasicTextField(
                    value = quantity.toString(),
                    onValueChange = { value ->
                        val newQuantity = value.toIntOrNull() ?: 0
                        onQuantityChange(newQuantity)
                    },
                    modifier = modifier.width(35.dp),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    enabled = true,
                    singleLine = true,
                )

                Icon(
                    painter = painterResource(R.drawable.icons8_plus),
                    contentDescription = "plus",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onIncrement()
                        }
                )
            }
        }
    }
}
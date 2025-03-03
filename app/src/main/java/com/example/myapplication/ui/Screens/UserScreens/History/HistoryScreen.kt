package com.example.myapplication.ui.Screens.UserScreens.History

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue
import kotlin.math.absoluteValue


@Composable
fun HistoryScreen(
    navController: NavController,
    warehouseId: String,
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {

    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "History",
                onBackClick = { navController.popBackStack() }
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Latest 20 History entries:",
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))



            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(history) { entry ->
                    val absQuantityChange = entry.quantityChange.absoluteValue ?: 0

                    HistoryLabel(
                        text = entry.itemName, // Item ID or other description
                        value = absQuantityChange.toString(),
                        date = entry.date, // Formatted timestamp as date
                        isPlus = entry.quantityChange > 0
                    )
                }
            }


        }


    }
}

@Composable
fun HistoryLabel(
    text: String,
    value: String,
    date: String,
    isPlus: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .height(40.dp),
        colors = CardDefaults.cardColors(GrayishBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(2f)

            ) {
                // Display the plus or minus icon based on quantity change
                if (isPlus) {
                    Icon(
                        painter = painterResource(R.drawable.icons8_plus),
                        contentDescription = "plus",
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.icons8_minus),
                        contentDescription = "minus",
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Display the value (quantity change)
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = Color.White,

                )
            }
            Spacer(modifier = Modifier.width(6.dp))


            // Display the item name with maxLines and ellipsize to handle overflow
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(3f)

            )

            Spacer(modifier = Modifier.width(6.dp))


            // Display the date with extra light font weight
            Text(
                text = date,
                color = Color.White,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(2f)
            )
        }
    }
}

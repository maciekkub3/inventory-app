package com.example.myapplication.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp, // Back arrow icon
                    contentDescription = "Back",
                )
            }
        },
        title = {
            Text(
                text = "Main menu",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF3C4C5E), // Custom background color
            titleContentColor = Color.White,   // Text color
            navigationIconContentColor = Color.White // Icon color
        )
    )
}

@Composable
fun EmptyBottomBar() {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E), // Background color of the bottom bar
        contentPadding = PaddingValues(0.dp), // Remove padding if needed
        actions = {
            // Empty content to match a minimal bottom bar design
            Spacer(modifier = Modifier.height(56.dp))
        }
    )
}

@Composable
fun TwoButtonsBottomBar(
    firstColor: Color,
    secondColor: Color,
    firstText: String,
    secondText: String,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E),
        contentPadding = PaddingValues(0.dp),

        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { onFirstButtonClick() },
                    colors = ButtonDefaults.buttonColors(firstColor),
                    modifier = Modifier.size(width = 150.dp, height = 35.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(text = firstText, color = Color.White)

                }

                Button(
                    onClick = { onSecondButtonClick() },
                    colors = ButtonDefaults.buttonColors(secondColor),
                    modifier = Modifier.size(width = 150.dp, height = 35.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp)

                ) {
                    Text(text = secondText, color = Color.White)

                }
            }

        }
    )
}

@Composable
fun DashboardBottomBar() {
    BottomAppBar(
        containerColor = Color(0xFF0F2027), // Background color of the bottom bar
        contentPadding = PaddingValues(0.dp), // Remove padding if needed
        actions = {
            // Empty content to match a minimal bottom bar design
            Spacer(modifier = Modifier.height(56.dp))
        }
    )
}




@Preview
@Composable
fun PreviewCustomBottomBar() {
    Scaffold(
        bottomBar = { AddProductBottomBar(
            firstColor = Color.Red,
            secondColor = Color(0xFF4C9DAF),
            firstText = "Cancel",
            secondText = "Apply",
            onFirstButtonClick = { /* Handle first button click */ },
            onSecondButtonClick = { /* Handle second button click */ },
            AddNewButtonClick = { /* Handle second button click */ }
        ) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black) // Background to simulate your UI
        )
    }
}



@Composable
fun PreviewCustomTopAppBar() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column {
            CustomTopAppBar(onBackClick = { /* Handle back */ })
        }
    }
}


@Composable
fun AddProductBottomBar(
    firstColor: Color,
    secondColor: Color,
    firstText: String,
    secondText: String,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    AddNewButtonClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E),
        contentPadding = PaddingValues(0.dp),

        actions = {
            Column(

            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF47505B))
                        .padding(5.dp),


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Total product Types: 10",
                            fontSize = 12.sp,
                            color = Color.White,

                        )
                        Button(
                            onClick = { AddNewButtonClick() },
                            colors = ButtonDefaults.buttonColors(Color.Green),
                            modifier = Modifier.size(width = 100.dp, height = 25.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(4.dp)
                        ) {
                            Text(text = firstText, color = Color.White)

                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = { onFirstButtonClick() },
                        colors = ButtonDefaults.buttonColors(firstColor),
                        modifier = Modifier.size(width = 150.dp, height = 35.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) {
                        Text(text = firstText, color = Color.White)

                    }

                    Button(
                        onClick = { onSecondButtonClick() },
                        colors = ButtonDefaults.buttonColors(secondColor),
                        modifier = Modifier.size(width = 150.dp, height = 35.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)

                    ) {
                        Text(text = secondText, color = Color.White)

                    }
                }
            }


        }
    )
}


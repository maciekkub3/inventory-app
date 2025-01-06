package com.example.myapplication.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.CharcoalBlue

@Composable
fun LogoutTopAppBar(onLogoutClick: () -> Unit, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3C4C5E)) // Custom background color
            .height(56.dp) // Define the height of the AppBar manually


    ) {
        // Navigation icon positioned to the left
        IconButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .align(Alignment.CenterStart) // Align the icon to the start (left)
                .padding(start = 16.dp) // Optional: Add padding to the icon
        ) {
            Icon(
                painter = painterResource(R.drawable.icons8_logout),
                tint = Color.White,
                contentDescription = "Logout",
                modifier = Modifier.size(40.dp) // Adjust icon size
            )
        }

        // Title aligned in the center
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center) // Center the title
        )
    }
}



@Composable
fun BackTopAppBar(onBackClick: () -> Unit, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3C4C5E)) // Custom background color
            .height(56.dp) // Define the height of the AppBar manually


    ) {
        // Navigation icon positioned to the left
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart) // Align the icon to the start (left)
                .padding(start = 16.dp) // Optional: Add padding to the icon
        ) {
            Icon(
                painter = painterResource(R.drawable.icons8_arrowback),
                tint = Color.White,
                contentDescription = "Back",
                modifier = Modifier.size(40.dp) // Adjust icon size
            )
        }

        // Title aligned in the center
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center) // Center the title
        )
    }
}


@Composable
fun BottomBarWithTextAndButton(
    text: String,
    onButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(CharcoalBlue) // Background color for the bottom bar
            .padding(horizontal = 8.dp)

    ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.weight(1f))

        // Right-aligned Button
        Button(
            onClick = onButtonClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Color.Green),
            modifier = Modifier
                .padding(end = 5.dp)

        ) {
            Text(text = "ADD NEW", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BottomBarWithText(
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(CharcoalBlue) // Background color for the bottom bar
            .padding(horizontal = 8.dp)

    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
        )


    }
}

@Preview
@Composable
fun BottomBarWithTextAndButtonPreview() {
    BottomBarWithTextAndButton("Available warehouses: 4", onButtonClick = {})
}


@Preview
@Composable
fun BackTopAppBarPreview() {
    BackTopAppBar(onBackClick = { /* Handle back */ }, title = "Main menu")
}

@Preview
@Composable
fun LogoutTopAppBarPreview() {
    LogoutTopAppBar(onLogoutClick = { /* Handle back */ }, title = "Main menu")
}

@Preview
@Composable
fun EmptyBottomBar() {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E), // Background color of the bottom bar
        contentPadding = PaddingValues(0.dp), // Remove padding if needed
        actions = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = "Available warehouses: 4",
                textAlign = TextAlign.Center,
            )
        }
    )
}

@Preview
@Composable
fun TwoButtonsBottomBarPreview() {
    TwoButtonsBottomBar(
        firstColor = Color.Red,
        secondColor = Color.Green,
        firstText = "CANCEL",
        secondText = "ADD",
        onFirstButtonClick = { /* Handle first button click */ },
        onSecondButtonClick = { /* Handle second button click */ }
    )

}

@Composable
fun BoxTwoButtonsBottomBar(
    firstColor: Color,
    secondColor: Color,
    firstText: String,
    secondText: String,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit
) {
    BottomAppBar(
        containerColor = CharcoalBlue,
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
fun TwoButtonsBottomBar(
    firstColor: Color,
    secondColor: Color,
    firstText: String,
    secondText: String,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit
) {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(CharcoalBlue),
            ) {
                Button(
                    onClick = { onFirstButtonClick() },
                    colors = ButtonDefaults.buttonColors(firstColor),
                    modifier = Modifier.size(width = 150.dp, height = 35.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(text = firstText, color = Color.White, fontWeight = FontWeight.Bold)

                }

                Button(
                    onClick = { onSecondButtonClick() },
                    colors = ButtonDefaults.buttonColors(secondColor),
                    modifier = Modifier.size(width = 150.dp, height = 35.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp)

                ) {
                    Text(text = secondText, color = Color.White, fontWeight = FontWeight.Bold)

                }
            }



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

@Preview
@Composable
fun PreviewCustomBottomBarr() {
    Scaffold(
        bottomBar = { TwoButtonsBottomBar(
            firstColor = Color.Red,
            secondColor = Color(0xFF4C9DAF),
            firstText = "Cancel",
            secondText = "Apply",
            onFirstButtonClick = { /* Handle first button click */ },
            onSecondButtonClick = { /* Handle second button click */ },
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
            LogoutTopAppBar(onLogoutClick = { /* Handle back */ }, title = "Main menu")
        }
    }
}


@Composable
fun AddProductBottomBar(
    firstColor: Color = Color.Red,
    secondColor: Color = Color.Green ,
    firstText: String = "CANCEL",
    secondText: String = "APPLY",
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    AddNewButtonClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF3C4C5E),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(110.dp),

        actions = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF47505B))
                        .padding(5.dp),

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)

                    ) {

                        Text(
                            text = "Total product Types: 10",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,

                        )

                        Box(
                            modifier = Modifier
                                .size(width = 100.dp, height = 20.dp)
                                .clickable{AddNewButtonClick()}
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Green)
                                .shadow(elevation = 10.dp),
                            contentAlignment = Alignment.Center



                        ) {
                            Text(
                                text = "ADD NEW",
                                color = Color.White,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                )

                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
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


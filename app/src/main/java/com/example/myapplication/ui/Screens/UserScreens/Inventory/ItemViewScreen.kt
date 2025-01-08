package com.example.myapplication.ui.Screens.UserScreens.Inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun ItemViewScreen(
    navController: NavController,

    ) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Item View",
                onBackClick = {navController.popBackStack()}
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "REMOVE",
                secondText = "ADD",
                onFirstButtonClick = {},
                onSecondButtonClick = {}
            )
        }

        )
    { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Drill",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Image(
                painter = painterResource(R.drawable.drill),
                contentDescription = "Drill",
                Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            ItemInfo()

            Spacer(modifier = Modifier.height(30.dp))

            ItemDescription()

            Spacer(modifier = Modifier.height(40.dp))

            AddOrRemove()



        }
    }

}

@Preview
@Composable
fun ItemInfo() {
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
                    text = "450",
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
                    text = "20$",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold

                )
            }

        }
    }
}

@Preview
@Composable
fun ItemDescription() {
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
            Text(
                text = "MAKITA HP1631J impact drill 710W MAKPAC (electronic)",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )


        }
    }
}

@Preview
@Composable
fun AddOrRemove() {
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
        )

        TextField(
            value = "85",
            onValueChange = {},
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
        )
    }
}
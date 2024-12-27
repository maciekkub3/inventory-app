@file:Suppress("UNREACHABLE_CODE")

package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.InformationLabel
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Preview
@Composable
fun EditProfileScreen() {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Edit Proflile Information",
                onBackClick = { /* Handle back */ }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "Cancel",
                secondText = "Apply",
                onFirstButtonClick = { /* Handle first button click */ },
                onSecondButtonClick = { /* Handle second button click */ }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkSlateGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Profile Image",
                Modifier
                    .size(300.dp)
                    .clip(CircleShape)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkGrayish)


            ) {
                Column(
                    modifier = Modifier
                        .padding(7.dp)
                ) {

                    InformationLabel(
                        dataType = "name",
                        dataValue = "Jan Kowalski",
                    )


                    InformationLabel(
                        dataType = "Email",
                        dataValue = "maciek.k2001@gmail.com",

                        )

                    InformationLabel(
                        dataType = "Phone Number",
                        dataValue = "+48 692 994 231",

                        )

                    InformationLabel(
                        dataType = "Adress",
                        dataValue = "Swarożyca 15a/6",

                        )
                }

            }
        }
    }
}


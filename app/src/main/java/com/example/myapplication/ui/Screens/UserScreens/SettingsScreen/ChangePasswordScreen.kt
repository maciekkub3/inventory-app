package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkSlateGray

@Preview
@Composable
fun ChangePasswordScreen(

) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Change Password",
                onBackClick = {}
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "Cancel",
                secondText = "Apply",
                onFirstButtonClick = {},
                onSecondButtonClick = {}
            )
        }
    )
    { innerPadding ->

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkSlateGray)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            txtField(title = "Current Password")
            txtField(title = "New Password")
            txtField(title = "Confirm New Password")
        }


    }
}

@Composable
fun txtField(title: String) {
    Column(
        modifier = Modifier

    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .offset(y = 10.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text("**********")},
            singleLine = true,

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
            ),
            shape = RoundedCornerShape(16.dp),

        )
    }
}

@Preview
@Composable
fun txtFieldPreview() {
    txtField(title = "Current Password")
}
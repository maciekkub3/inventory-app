package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun SettingsScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                onBackClick = { navController.popBackStack() },
                title = "Settings"
            )
        },
    ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkSlateGray)
                    .padding(innerPadding)
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                SettingsButton(
                    "Edit Profile Information",
                    onClick = { navController.navigate(Screen.EditProfile.route) })
                SettingsButton(
                    "Change Password",
                    onClick = { navController.navigate(Screen.ChangePassword.route) })
            }

    }


}


@Composable
fun SettingsButton(text: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = GrayishBlue),
        shape = RoundedCornerShape(15.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 1.dp,
            pressedElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp) // Add padding to move text further left
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = NavController(LocalContext.current))
}

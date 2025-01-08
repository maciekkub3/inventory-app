package com.example.myapplication.ui.Screens.UserScreens.ReportsScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun ReportsScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                onBackClick = { navController.popBackStack() },
                title = "Reports"
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                ReportsButton("Current stock levels", onClick = { })
                ReportsButton("Low stock report", onClick = { })
                ReportsButton("Inventory activity history", onClick = {})
                ReportsButton("Recent logins", onClick = { })

            }
        }
    }


}


@Composable
fun ReportsButton(text: String, onClick: () -> Unit) {
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


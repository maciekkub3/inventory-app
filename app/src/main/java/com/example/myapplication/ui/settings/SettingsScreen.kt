package com.example.myapplication.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF47505B))
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            SettingsButton("Edit Profile Information")
            SettingsButton("Change Password")
            SettingsButton("Language Selection")
        }
    }

}


@Composable
fun SettingsButton(text: String) {
    ElevatedButton(
        onClick = { /*TODO*/ },
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF58616D)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(15.dp),


        modifier = Modifier

            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            ,
            
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
    SettingsScreen()
}

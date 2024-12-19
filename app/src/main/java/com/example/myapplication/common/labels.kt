package com.example.myapplication.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun labelPreview() {
    Column(

    ) {
        label("Name", "Jan Kowalski")
        label("Role", "Contractor")
        label("Email", "Maciek.k2001@gmail.com")
        label ("Phone Number", "+48 692 994 231")
    }

}

@Composable
fun label(
    dataType: String,
    dataValue: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            .background(Color(0xFF4D4D4D))
            .padding(7.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = dataType,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(25.dp))
            Box(
                modifier = Modifier

                    .background(
                        Color(0xFF323232),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dataValue,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 16.sp,
                    modifier = Modifier


                )
            }
        }
    }
}































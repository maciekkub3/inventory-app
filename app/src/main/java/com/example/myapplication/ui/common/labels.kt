package com.example.myapplication.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.CharcoalBlue
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkWeathered
import com.example.myapplication.ui.theme.GrayishBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangableInformationTextField(
    dataType: String,
    userInput: String,
    modifier: Modifier = Modifier.padding(vertical = 5.dp),
    isNumeric: Boolean,
    onValueChange: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            .background(Dark),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = dataType,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(25.dp))

            TextField(
                value = userInput,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = DarkWeathered
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}






@Composable
fun InformationLabel(
    dataType: String,
    dataValue: String,
    modifier: Modifier = Modifier.padding(vertical = 5.dp),

) {
    Box(
        modifier = modifier
            .height(45.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            .background(Dark),

        contentAlignment = Alignment.Center

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()

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
                    .fillMaxHeight()
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .background(
                        DarkWeathered,
                        shape = RoundedCornerShape(10.dp)
                    )

                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dataValue,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    modifier = Modifier


                )
            }
        }
    }
}


@Composable
fun ItemRow(
    id: String,
    quantity: String,
    item: String,
    lastRestock: String,
    imageUrl: String,
    price: String,
    onItemClick: (String) -> Unit // Lambda accepts itemId
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp)
            ) // Apply shadow with rounded corners
            .background(
                GrayishBlue,
                RoundedCornerShape(10.dp)
            ) // Background with rounded corners
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable { onItemClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Quantity Text
            Box(
                modifier = Modifier
                    .width(45.dp), // Fixed width for consistent alignment
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quantity,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                "x",
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .offset(y = (-1).dp),
                color = Color.White,
                fontWeight = FontWeight.Light
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item,
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f) // Take up remaining space without affecting others

        ) {
            Text(
                text = item,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1, // Limit the text to 1 line
                overflow = TextOverflow.Ellipsis, // Add ellipsis if the text overflows
                textAlign = TextAlign.Center
            )
            Text(
                text = "$price$",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Last Restock Text with fixed width for alignment
        Box(
            modifier = Modifier
                .width(85.dp), // Fixed width to prevent shifting based on text length
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                if (lastRestock.toInt() > 0) {
                    if (lastRestock.toInt() == 1) {
                        " +$lastRestock pc"
                    } else {
                        " +$lastRestock pcs"
                    }
                } else {
                    if (lastRestock.toInt() == -1) {
                        "$lastRestock pc"
                    } else {
                        "$lastRestock pcs"
                    }
                },
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

    }
}







@Composable
fun SearchLabel(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(CharcoalBlue)
            .bottomBorder(1.dp, Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            shape = RoundedCornerShape(10.dp),
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) },
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Search...",
                        color = Color.Black,
                        modifier = Modifier
                    )
                }
            },
            textStyle = TextStyle(color = Color.Black),

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}































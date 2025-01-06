package com.example.myapplication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.CharcoalBlue
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkWeathered
import com.example.myapplication.ui.theme.GrayishBlue


@Preview
@Composable
fun InformationlabelPreview() {
    Column(

    ) {

        InformationTextField("textfield", "Jan Kowalski")
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationTextField(
    dataType: String,
    userInput: String,
    modifier: Modifier = Modifier.padding(vertical = 5.dp),
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

            TextField(
                value = userInput,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = DarkWeathered
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f) // Use weight to dynamically allocate space
            )
        }
    }
}


@Composable
fun InformationLabel(
    dataType: String,
    dataValue: String,
    modifier: Modifier = Modifier.padding(vertical = 5.dp)
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
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 16.sp,
                    modifier = Modifier


                )
            }
        }
    }
}


@Composable
fun HistoryLabel(
    text: String,
    value: String,
    date: String,
    isPlus: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .height(40.dp),
        colors = CardDefaults.cardColors(GrayishBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(),


        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),


            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(isPlus) {
                    Icon(
                        painter = painterResource(R.drawable.icons8_plus),
                        contentDescription = "plus",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(35.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.icons8_minus),
                        contentDescription = "minus",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }

                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                )
            }
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier,
                fontSize = 20.sp
            )
            Text(
                text = date,
                color = Color.White,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
            )
        }
    }
}

//@Preview
@Composable
fun HistoryLabelPreview() {
    HistoryLabel("Coca Cola", "85", isPlus = true, date = "20.11.2001")
}


//TODO DO NAPRAWY
@Composable
fun AddProductLabel(
    name: String,
    image: Painter,
    modifier: Modifier = Modifier.padding(horizontal = 10.dp)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(GrayishBlue),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(),


        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {

            Image(
                painter = image,
                contentDescription = "plus",
                modifier = Modifier
                    .size(35.dp)
            )
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

            ) {
                Icon(
                    painter = painterResource(R.drawable.icons8_minus),
                    contentDescription = "minus",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(30.dp)
                )

                AmountTextField(
                    value = "85",
                    onValueChange = {},
//                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .width(50.dp),



                )

                Icon(
                    painter = painterResource(R.drawable.icons8_plus),
                    contentDescription = "plus",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
//        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(0.dp), // this is how you can remove the padding
        )
    }
}

@Preview
@Composable
fun AddProductLabelPreview() {
    AddProductLabel("Coca Cola", painterResource(id = R.drawable.colka))
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































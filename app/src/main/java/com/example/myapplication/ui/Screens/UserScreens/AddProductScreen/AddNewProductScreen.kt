package com.example.myapplication.ui.Screens.UserScreens.AddProductScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.ChangableInformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray


@Composable
fun AddNewProductScreen(
    warehouseId: String,
    navController: NavController,
    viewModel: AddNewProductViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateImageUri(it) }
    }

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Add New Product",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "CANCEL",
                secondText = "ADD PRODUCT",
                onFirstButtonClick = { navController.popBackStack() },
                onSecondButtonClick = { viewModel.addProduct(navController) }
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Dark)
                    .clickable { launcher.launch("image/*") } // Launch image picker on click
            ) {
                if (uiState.imageUri != null) {
                    // Display selected image using Coil's AsyncImage
                    AsyncImage(
                        model = uiState.imageUri,
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop, // Ensures the image fills the entire box
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)

                    )
                } else {
                    // Placeholder camera icon
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

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

                    ChangableInformationTextField(
                        dataType = "Item",
                        userInput = uiState.name,
                        onValueChange = { viewModel.updateName(it) },
                        isNumeric = false

                    )


                    ChangableInformationTextField(
                        dataType = "Price",
                        userInput = uiState.price,
                        onValueChange = { viewModel.updatePrice(it) },
                        isNumeric = true

                    )

                    ChangableInformationTextField(
                        dataType = "Description",
                        userInput = uiState.description,
                        onValueChange = { viewModel.updateDescription(it) },
                        isNumeric = false

                    )
                    ChangableInformationTextField(
                        dataType = "Size",
                        userInput = uiState.size,
                        onValueChange = { viewModel.updateSize(it) },
                        isNumeric = true

                    )

                }

            }
        }
    }
}


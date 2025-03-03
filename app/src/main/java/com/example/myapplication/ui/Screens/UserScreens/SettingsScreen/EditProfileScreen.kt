@file:Suppress("UNREACHABLE_CODE")

package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.ChangableInformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val userId = viewModel.userId

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Edit Proflile Information",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "Cancel",
                secondText = "Apply",
                onFirstButtonClick = { navController.popBackStack() },
                onSecondButtonClick = {
                    viewModel.saveUserDetails(
                        userId!!,
                        onSuccess = {
                            navController.navigateUp() // Navigate back on success
                        },
                        onError = { exception ->
                            // Show error message to the user
                            Log.e(
                                "UserDetailScreen",
                                "Error saving user details: ${exception.message}"
                            )
                        })
                }
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
            ) {

                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = "User Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )

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
                        dataType = "Name",
                        userInput = state.name,
                        onValueChange = { newName -> viewModel.updateUserName(newName) },
                        isNumeric = false


                    )
                    ChangableInformationTextField(
                        dataType = "Phone Number",
                        userInput = state.phoneNumber,
                        onValueChange = { newPhoneNumber ->
                            viewModel.updateUserPhoneNumber(
                                newPhoneNumber
                            )
                        },
                        isNumeric = true

                    )
                    ChangableInformationTextField(
                        dataType = "Adress",
                        userInput = state.address,
                        onValueChange = { newAddress -> viewModel.updateUserAddress(newAddress) },
                        isNumeric = false

                    )

                }

            }
        }
    }
}


package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.ChangableInformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun UserDetailScreen(
    navController: NavController,
    userId: String?,
    viewModel: UserDetailViewModel = hiltViewModel() // Get ViewModel via Hilt
) {


    val state = viewModel.state.collectAsState().value

    // Launcher for image picking (optional for changing profile picture)
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateUserImage(it) }
    }

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "User Details",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "REMOVE",
                secondText = "SAVE",
                onFirstButtonClick = {viewModel.removeUser(navController)},
                onSecondButtonClick = {
                    viewModel.saveUserDetails(
                        userId = userId ?: "",
                        onSuccess = {
                            navController.navigate(Screen.OwnerUsersView.route) {
                                // Optionally, pop the back stack so that users can't go back to the "Add Warehouse" screen
                                popUpTo(route = "userView/{userId}") { inclusive = true }
                                popUpTo(Screen.OwnerUsersView.route) { inclusive = true }
                            }
                        },
                        onError = { exception ->
                            // Show error message to the user
                            Log.e("UserDetailScreen", "Error saving user details: ${exception.message}")
                        }
                    )
                } // Save action
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkSlateGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Dark)
                    .clickable { launcher.launch("image/*") } // Optional: Allow changing the profile image
            ) {
                AsyncImage(
                    model = state.imageUri,
                    contentDescription = "User Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Information Fields
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkGrayish)
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    ChangableInformationTextField(
                        dataType = "Name",
                        userInput = state.name,
                        onValueChange = { newName -> viewModel.updateUserName(newName) },
                        isNumeric = false

                    )
                    ChangableInformationTextField(
                        dataType = "Email",
                        userInput = state.email,
                        onValueChange = { newEmail -> viewModel.updateUserEmail(newEmail) },
                        isNumeric = false

                    )

                    ChangableInformationTextField(
                        dataType = "Phone Number",
                        userInput = state.phoneNumber,
                        onValueChange = { newPhone -> viewModel.updateUserPhoneNumber(newPhone) },
                        isNumeric = true

                    )
                    ChangableInformationTextField(
                        dataType = "Address",
                        userInput = state.address,
                        onValueChange = { newAddress -> viewModel.updateUserAddress(newAddress) },
                        isNumeric = false

                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        RadioButton(
                            selected = state.role == "Admin",
                            onClick = { viewModel.updateUserRole("Admin") }
                        )
                        Text(text = "Admin", color = Color.White)

                        Spacer(modifier = Modifier.weight(3f))

                        RadioButton(
                            selected = state.role == "Worker",
                            onClick = { viewModel.updateUserRole("Worker") }
                        )
                        Text(text = "Worker", color = Color.White)

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

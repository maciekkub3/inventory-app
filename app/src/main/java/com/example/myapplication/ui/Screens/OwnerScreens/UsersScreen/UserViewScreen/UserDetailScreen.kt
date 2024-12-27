package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.InformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun UserDetailScreen(
    navController: NavController,
    userId: String?,
    viewModel: UserDetailViewModel = hiltViewModel() // Get ViewModel via Hilt
) {

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.fetchUserDetails(it) // Fetch warehouse details from Firebase
        }
    }

    val state = viewModel.state.collectAsState().value


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = state.name,
                onBackClick = {navController.navigateUp()}
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "CANCEL",
                secondText = "SAVE",
                onFirstButtonClick = { navController.navigateUp() },
                onSecondButtonClick = { /* Handle second button click */ }
            )
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)
        ) {

            InformationTextField(
                dataType = "Name",
                userInput = state.name,
                onValueChange = { newName ->
                    viewModel.updateUserName(newName) // Update the name in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display and edit the warehouse space
            InformationTextField(
                dataType = "role",
                userInput = state.role,
                onValueChange = { newRole ->
                    viewModel.updateUserRole(newRole) // Update the space in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            InformationTextField(
                dataType = "email",
                userInput = state.email,
                onValueChange = { newEmail ->
                    viewModel.updateUserEmail(newEmail) // Update the space in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            InformationTextField(
                dataType = "phone Number",
                userInput = state.phoneNumber,
                onValueChange = { newPhoneNumber ->
                    viewModel.updateUserEmail(newPhoneNumber) // Update the space in ViewModel
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            InformationTextField(
                dataType = "Address",
                userInput = state.address,
                onValueChange = { newAddress ->
                    viewModel.updateUserEmail(newAddress) // Update the space in ViewModel
                }
            )




        }
    }
}
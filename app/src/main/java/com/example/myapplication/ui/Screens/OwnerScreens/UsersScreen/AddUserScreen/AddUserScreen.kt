package com.example.myapplication.ui.Screens.AddProductScreen

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.InformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.signin.AddUserViewModel
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun AddUserScreen(
    navController: NavController,
    viewModel: AddUserViewModel = hiltViewModel(),
    ) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Add User",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "CANCEL",
                secondText = "ADD USER",
                onFirstButtonClick = { /* Handle first button click */ },
                onSecondButtonClick = { viewModel.createAccount() }
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
                    .shadow(elevation = 1.dp, shape = CircleShape)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(150.dp)
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


                    InformationTextField(
                        dataType = "Name",
                        userInput = state.name,
                        onValueChange = {viewModel.updateName(it)}
                    )
                    InformationTextField(
                        dataType = "Role",
                        userInput = state.role,
                        onValueChange = {viewModel.updateRole(it)}
                    )
                    InformationTextField(
                        dataType = "Email",
                        userInput = state.email,
                        onValueChange = {viewModel.updateUserEmail(it)}
                    )
                    InformationTextField(
                        dataType = "Password",
                        userInput = state.password,
                        onValueChange = {viewModel.updatePassword(it)}
                    )
                    InformationTextField(
                        dataType = "Phone Number",
                        userInput = state.phoneNumber,
                        onValueChange = {viewModel.updatePhoneNumber(it)}
                    )
                    InformationTextField(
                        dataType = "Adress",
                        userInput = state.Adress,
                        onValueChange = {viewModel.updateAdress(it)}
                    )



                }

            }
        }
    }
}


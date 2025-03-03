package com.example.myapplication.ui.Screens.UserScreens.SettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.DarkSlateGray
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Local state for the text fields
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Change Password",
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
                    if (newPassword != confirmNewPassword) {
                        errorMessage = "New passwords do not match."
                        return@TwoButtonsBottomBar
                    }
                    if (newPassword.isEmpty() || currentPassword.isEmpty()) {
                        errorMessage = "All fields are required."
                        return@TwoButtonsBottomBar
                    }
                    if (newPassword == currentPassword) {
                        errorMessage = "new password can't be the same as current."
                        return@TwoButtonsBottomBar
                    }
                    scope.launch {
                        viewModel.changePassword(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            onSuccess = {
                                errorMessage = null
                                navController.popBackStack()
                            },
                            onError = { message ->
                                errorMessage = message
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkSlateGray)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
            PasswordTextField(
                title = "Current Password",
                value = currentPassword,
                onValueChange = { currentPassword = it },
            )
            PasswordTextField(
                title = "New Password",
                value = newPassword,
                onValueChange = { newPassword = it }
            )
            PasswordTextField(
                title = "Confirm New Password",
                value = confirmNewPassword,
                onValueChange = { confirmNewPassword = it }
            )
        }
    }
}

@Composable
fun PasswordTextField(title: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = title, color = Color.White, fontWeight = FontWeight.Light) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White)

        )
    }
}

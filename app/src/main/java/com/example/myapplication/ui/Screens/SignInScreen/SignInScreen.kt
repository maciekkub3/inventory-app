package com.example.myapplication.ui.Screens.SignInScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.signin.SignInScreenEvent
import com.example.myapplication.ui.signin.SignInViewModel
import com.example.myapplication.ui.theme.backgroundGradientBrush


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: SignInViewModel = hiltViewModel(),

    ) {
    val state = viewModel.state.collectAsState().value
    val events = viewModel.events.collectAsState(initial = null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradientBrush)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = "Person",
                tint = Color.White,
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = state.userEmail,
                onValueChange = { viewModel.onEvent(SignInScreenEvent.OnUserEmailChanged(it)) },
                label = {
                    Text(
                        text = "Username",
                        color = Color.White,
                    )
                },
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.userPassword,
                onValueChange = { viewModel.onEvent(SignInScreenEvent.OnUserPasswordChanged(it)) },
                label = {
                    Text(
                        text = "Password",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )

            ElevatedButton(
                onClick = { viewModel.onEvent(SignInScreenEvent.OnSignInClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Forgot Password?",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),

                    )
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }


        }
    }
}

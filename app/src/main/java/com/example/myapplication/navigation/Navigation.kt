/*
package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.signin.SignInScreen
import com.example.myapplication.ui.signin.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {
            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsState()

            LaunchedEffect(key1 = viewModel.events) {
                viewModel.events.onEach { event ->
                    when (event) {

                    }
                }.launchIn(CoroutineScope(Dispatchers.Main.immediate))
            }

            SignInScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = Screen.OwnerMenu.route) {

        }
    }
}*/

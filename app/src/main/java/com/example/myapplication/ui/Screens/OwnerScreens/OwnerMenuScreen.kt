package com.example.myapplication.ui.Screens.OwnerScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.Screens.UserScreens.SettingsScreen.SettingsButton
import com.example.myapplication.ui.common.LogoutTopAppBar
import com.example.myapplication.ui.theme.DarkSlateGray


@Composable
fun OwnerMenuScreen(
    navController: NavController,
    viewModel: OwnerMenuViewModel = hiltViewModel()

) {

    Scaffold(
        topBar = {
            LogoutTopAppBar(
                onLogoutClick = { viewModel.signOut(navController) },
                title = "Main Menu"
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding)
                .padding(vertical = 20.dp, horizontal = 20.dp)

        ) {
            SettingsButton("Warehouses", onClick = { navController.navigate(Screen.OwnerWarehouseMenu.route) })
            SettingsButton("Users", onClick = { navController.navigate(Screen.OwnerUsersView.route)})
        }
    }




}

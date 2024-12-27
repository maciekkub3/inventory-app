package com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserListScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.domain.model.User
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.BottomBarWithTextAndButton
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun UsersScreen(
    navController: NavController, // Pass the NavController here
    viewModel: UsersScreenViewModel = hiltViewModel(),
) {
    val users by viewModel.users.collectAsState()
    val usersCount = users.size

    LaunchedEffect(key1 = viewModel.navigateToUserDetails) {
        viewModel.navigateToUserDetails.collect { userId ->
            navController.navigate("userView/$userId") // Trigger navigation
        }
    }


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Users",
                onBackClick = {navController.navigateUp()}
            )
        },
        bottomBar = {
            BottomBarWithTextAndButton("Total Users: $usersCount", onButtonClick = {navController.navigate(Screen.OwnerAddUsers.route)})
        }
    )
    { innerPadding ->

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray) // Background color
        ) {
            items(users) { user ->
                UserItem(user, onUserClick = {
                    // Navigate to the WarehouseDetailsScreen when clicked
                    viewModel.onUserClick(user.id)
                })
            }
        }

    }
}


@Composable
fun UserItem(user: User, onUserClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onUserClick() }
        ,

        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrayishBlue)
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.name, color = Color.White)
                Text(text = user.email, color = Color.White)
            }
        }
    }
}
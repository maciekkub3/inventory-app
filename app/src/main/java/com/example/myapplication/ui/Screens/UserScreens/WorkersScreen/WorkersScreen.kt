package com.example.myapplication.ui.Screens.UserScreens.WorkersScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.domain.model.User
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.BottomBarWithText
import com.example.myapplication.ui.common.SearchLabel
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.GrayishBlue


@Composable
fun WorkersScreen(
    warehouseId: String,
    navController: NavController,
    viewModel: WorkersScreenViewModel = hiltViewModel(),
) {
    val users by viewModel.filteredUsers.collectAsState() // Observe filtered users
    val usersCount = users.size
    val searchQuery by viewModel.searchQuery.collectAsState() // Observe search query

    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Workers",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomBarWithText("Total Workers: $usersCount",)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(DarkSlateGray)
                .padding(innerPadding)

        ) {
            SearchLabel(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    viewModel.onSearchQueryChanged(query)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(users) { user ->
                    UserItem(user, onUserClick = {
                        viewModel.onUserClick(user.id)
                    })
                }
            }
        }
    }
}


@Composable
fun UserItem(user: User, onUserClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onUserClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrayishBlue)
                .padding(vertical = 6.dp, horizontal = 4.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Dark)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.imageUrl), // Coil to load image from URL
                    contentDescription = "User Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = user.email, color = Color.White, fontWeight = FontWeight.Light)
            }

            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "More options",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}

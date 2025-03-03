package com.example.myapplication.ui.Screens.UserScreens.WorkersScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen.WorkersViewViewModel
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.InformationLabel
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray

@Composable
fun WorkersViewScreen(
    navController: NavController,
    userId: String?,
    viewModel: WorkersViewViewModel = hiltViewModel() // Get ViewModel via Hilt
) {


    val state = viewModel.state.collectAsState().value

    // Launcher for image picking (optional for changing profile picture)


    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Worker Details",
                onBackClick = { navController.navigateUp() }
            )
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkSlateGray)
                .padding(innerPadding),
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
            ) {
                if (state.imageUri != null) {
                    AsyncImage(
                        model = state.imageUri,
                        contentDescription = "User Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(150.dp)
                    )
                }
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
                    InformationLabel(
                        dataType = "Name",
                        dataValue = state.name,
                    )
                    InformationLabel(
                        dataType = "Email",
                        dataValue = state.email,
                    )

                    InformationLabel(
                        dataType = "Phone Number",
                        dataValue = state.phoneNumber,
                    )
                    InformationLabel(
                        dataType = "Address",
                        dataValue = state.address,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))


                    }
                }
            }
        }
    }
}

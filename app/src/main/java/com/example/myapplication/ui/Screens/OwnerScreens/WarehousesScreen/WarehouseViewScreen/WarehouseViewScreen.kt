package com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myapplication.ui.common.BackTopAppBar
import com.example.myapplication.ui.common.ChangableInformationTextField
import com.example.myapplication.ui.common.TwoButtonsBottomBar
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.DarkGrayish
import com.example.myapplication.ui.theme.DarkSlateGray
import com.example.myapplication.ui.theme.DarkWeathered

@Composable
fun WarehouseDetailsScreen(
    navController: NavController,
    warehouseId: String?,
    viewModel: WarehouseViewViewModel = hiltViewModel() // Get ViewModel via Hilt
) {

//    LaunchedEffect(warehouseId) {
//        warehouseId?.let {
//            viewModel.fetchWarehouseDetails(it) // Fetch warehouse details from Firebase
//        }
//    }

    val state = viewModel.state.collectAsState().value

    var expanded by remember { mutableStateOf(false) } // For the dropdown expansion


    val userCount = state.availableUsers.size




    Scaffold(
        topBar = {
            BackTopAppBar(
                title = state.name,
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            TwoButtonsBottomBar(
                firstColor = Color.Red,
                secondColor = Color.Green,
                firstText = "REMOVE",
                secondText = "SAVE",
                onFirstButtonClick = {viewModel.removeWarehouse(navController) },
                onSecondButtonClick = {
                    if (warehouseId != null) {
                        viewModel.saveWarehouseDetails(
                            warehouseId = warehouseId,
                            onSuccess = {
                                navController.navigateUp() // Navigate back on success
                            },
                            onError = { exception ->
                                Log.e(
                                    "WarehouseViewScreen",
                                    "Error saving warehouse details: ${exception.message}"
                                )
                            }
                        )
                    }
                }
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


            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkGrayish)
            ) {
                Column(
                    modifier = Modifier
                        .padding(7.dp)
                ) {
                    // Input fields for warehouse name and space
                    ChangableInformationTextField(
                        dataType = "Name",
                        userInput = state.name,
                        onValueChange = { viewModel.updateWarehouseName(it) },
                        isNumeric = false

                    )
                    ChangableInformationTextField(
                        dataType = "Space",
                        userInput = state.space,
                        onValueChange = { viewModel.updateWarehouseSpace(it) },
                        isNumeric = true

                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded } // Toggle expansion on click
                            .background(Dark, RoundedCornerShape(8.dp)) // Background for the box
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            // Text for "Assign Owner"
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(35.dp)
                            ) {

                                Text(
                                    text = if (state.selectedOwner != null) "Owner: ${state.selectedOwner?.name}" else "Choose Owner",
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown Arrow",
                                    tint = Color.Gray
                                )
                            }

                            // If expanded, show the admin users list
                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Column {
                                    state.adminUsers.forEach { admin ->

                                        Column(
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.updateSelectedOwner(admin) // Update the ViewModel
                                                    expanded =
                                                        false // Collapse the box after selection
                                                }
                                        ) {
                                            Text(
                                                text = admin.name,
                                                color = Color.White,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp)
                                            )
                                            Text(
                                                text = admin.email,
                                                color = Color.White,
                                                fontWeight = FontWeight.ExtraLight,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp)
                                            )
                                            Divider(thickness = 1.dp, color = Color.Gray)
                                        }

                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    // User selection list
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Dark)
                    ) {

                        Column(
                            modifier = Modifier.height(310.dp)
                        ) {
                            Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Assign Workers", color = Color.White)
                                Text(
                                    "Available ($userCount)",
                                    color = Color.White,
                                    fontWeight = FontWeight.Light
                                )

                            }
                            Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                            ) {
                                items(state.availableUsers) { user -> // Use items instead of forEach

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(DarkWeathered)
                                            .padding(vertical = 8.dp, horizontal = 4.dp)
                                    ) {
                                        // Profile picture from imageUrl
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
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

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // User name and email
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = user.name,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = user.email,
                                                color = Color.White.copy(alpha = 0.7f),
                                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        // Checkbox
                                        Checkbox(
                                            checked = state.selectedUsers.contains(user),
                                            onCheckedChange = { isChecked ->
                                                val updatedList = if (isChecked) {
                                                    state.selectedUsers + user
                                                } else {
                                                    state.selectedUsers - user
                                                }
                                                viewModel.updateSelectedUsers(updatedList)
                                            }
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp)) // Spacer between rows
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}


package com.example.myapplication.navigation

import QRScannerScreen
import ScannedProductScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.Screens.AddProductScreen.AddUserScreen
import com.example.myapplication.ui.Screens.OwnerScreens.OwnerMenuScreen
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserListScreen.UsersScreen
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserListScreen.UsersScreenViewModel
import com.example.myapplication.ui.Screens.OwnerScreens.UsersScreen.UserViewScreen.UserDetailScreen
import com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.AddWarehouseScreen.AddWarehouseScreen
import com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.ChooseWarehouseScreen.ChooseWarehouseScreen
import com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.ChooseWarehouseScreen.ChooseWarehouseViewModel
import com.example.myapplication.ui.Screens.OwnerScreens.WarehousesScreen.WarehouseViewScreen.WarehouseDetailsScreen
import com.example.myapplication.ui.Screens.SignInScreen.LoginScreen
import com.example.myapplication.ui.Screens.UserScreens.AddProductScreen.AddNewProductScreen
import com.example.myapplication.ui.Screens.UserScreens.AddProductScreen.AddProductScreen
import com.example.myapplication.ui.Screens.UserScreens.ChooseWarehousesUserScreen.ChooseWarehouseUserScreen
import com.example.myapplication.ui.Screens.UserScreens.ChooseWarehousesUserScreen.ChooseWarehouseUserViewModel
import com.example.myapplication.ui.Screens.UserScreens.History.HistoryScreen
import com.example.myapplication.ui.Screens.UserScreens.Inventory.InventoryScreen
import com.example.myapplication.ui.Screens.UserScreens.Inventory.ItemViewScreen
import com.example.myapplication.ui.Screens.UserScreens.MainScreen.MainScreen
import com.example.myapplication.ui.Screens.UserScreens.ReportsScreen.ReportsScreen
import com.example.myapplication.ui.Screens.UserScreens.SettingsScreen.ChangePasswordScreen
import com.example.myapplication.ui.Screens.UserScreens.SettingsScreen.EditProfileScreen
import com.example.myapplication.ui.Screens.UserScreens.SettingsScreen.SettingsScreen
import com.example.myapplication.ui.Screens.UserScreens.WorkersScreen.WorkersScreen
import com.example.myapplication.ui.Screens.UserScreens.WorkersScreen.WorkersScreenViewModel
import com.example.myapplication.ui.Screens.UserScreens.WorkersScreen.WorkersViewScreen
import com.example.myapplication.ui.signin.SignInViewModel
import com.example.myapplication.ui.signin.SignInViewModelEvent
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
                        SignInViewModelEvent.SignInAsOwnerSuccessful -> {
                            navController.navigate(Screen.OwnerMenu.route) {
                                popUpTo(Screen.SignIn.route) { inclusive = true }
                            }
                        }

                        SignInViewModelEvent.SignInAsWorkerSuccessful -> {
                            navController.navigate(Screen.WarehouseMenu.route) {
                                popUpTo(Screen.SignIn.route) { inclusive = true }
                            }
                        }

                        else -> {
                            // Handle unknown or error events if needed
                        }
                    }
                }.launchIn(CoroutineScope(Dispatchers.Main.immediate))
            }
            LoginScreen(viewModel = viewModel)
        }
        composable(route = Screen.OwnerMenu.route) {
            OwnerMenuScreen(navController = navController)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }
        composable(route = Screen.ChangePassword.route) {
            ChangePasswordScreen(navController = navController)
        }
        /*composable(route = Screen.QRScan.route) {
            QRCodeScannerScreen(navController = navController)
        }
        composable(route = "${Screen.ScannedProducts.route}/{scannedData}") { backStackEntry ->
            val scannedData = backStackEntry.arguments?.getString("scannedData") ?: ""
            ReviewScreen(navController, scannedData)
        }
*/

        composable(route = "${Screen.Reports.route}/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let {id ->
                ReportsScreen(navController = navController, context = navController.context, warehouseId = id)
            }
        }






        composable(route = Screen.OwnerWarehouseMenu.route) {
            val chooseWarehouseViewModel: ChooseWarehouseViewModel = hiltViewModel()
            LaunchedEffect(chooseWarehouseViewModel.navigateToWarehouseDetails) {
                chooseWarehouseViewModel.navigateToWarehouseDetails.collect { warehouseId ->
                    navController.navigate("warehouseView/$warehouseId")
                }
            }
            ChooseWarehouseScreen(navController = navController)
        }

        composable(route = Screen.OwnerAddUsers.route) {
            AddUserScreen(navController = navController)
        }
        composable(route = Screen.OwnerAddWarehouse.route) {
            AddWarehouseScreen(navController = navController)
        }
        composable(route = Screen.OwnerUsersView.route) {
            val usersScreenViewModel: UsersScreenViewModel = hiltViewModel()
            LaunchedEffect(usersScreenViewModel.navigateToUserDetails) {
                usersScreenViewModel.navigateToUserDetails.collect { userId ->
                    navController.navigate("userView/$userId")
                }
            }
            UsersScreen(navController = navController)
        }
        composable(route = Screen.WarehouseMenu.route) {
            val chooseWarehouseUserViewModel: ChooseWarehouseUserViewModel = hiltViewModel()
            LaunchedEffect(chooseWarehouseUserViewModel.navigateToMainScreen) {
                chooseWarehouseUserViewModel.navigateToMainScreen.collect { warehouseId ->
                    navController.navigate("mainPage/$warehouseId")
                }
            }
            ChooseWarehouseUserScreen(navController = navController)
        }


        composable(route = "userView/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            userId?.let {
                UserDetailScreen(userId = it, navController = navController)
            }
        }


        composable(route = "warehouseView/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let {
                WarehouseDetailsScreen(warehouseId = it, navController = navController)
            }
        }

        composable(route = "mainPage/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let {
                MainScreen(warehouseId = it, navController = navController)
            }
        }

        composable(route = "AddProduct/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                AddProductScreen(navController = navController, warehouseId = id)
            }
        }
        composable(route = "AddNewProduct/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                AddNewProductScreen(navController = navController, warehouseId = id)
            }
        }

        composable(route = "Workers/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                WorkersScreen(navController = navController, warehouseId = id)
            }
            val workersScreenViewModel: WorkersScreenViewModel = hiltViewModel()
            LaunchedEffect(workersScreenViewModel.navigateToUserDetails) {
                workersScreenViewModel.navigateToUserDetails.collect { userId ->
                    navController.navigate("workerView/$userId")
                }
            }
        }
        composable(route = "workerView/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            userId?.let {
                WorkersViewScreen(userId = it, navController = navController)
            }
        }

        composable(route = "qrScanner/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                QRScannerScreen(
                    onQrCodeScanned = { scannedValue ->
                        navController.navigate("scannedProduct/$warehouseId/$scannedValue")
                        Log.d("QRScanner", "Scanned: $scannedValue")
                    },
                    onBack = { navController.popBackStack() },
                    warehouseId = id
                )
            }
        }

        composable("scannedProduct/{warehouseId}/{scannedValue}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId") ?: ""
            val scannedValue = backStackEntry.arguments?.getString("scannedValue") ?: ""

            ScannedProductScreen(
                warehouseId = warehouseId,
                scannedValue = scannedValue,
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(route = "${Screen.Inventory.route}/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                InventoryScreen(navController = navController, warehouseId = id)
            }
        }

        composable(route = "${Screen.History.route}/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            warehouseId?.let { id ->
                HistoryScreen(navController = navController, warehouseId = id)
            }
        }


        composable(route = "${Screen.ItemView.route}/{itemId}/{warehouseId}") { backStackEntry ->
            val warehouseId = backStackEntry.arguments?.getString("warehouseId")
            val itemId = backStackEntry.arguments?.getString("itemId")
            itemId?.let { id ->
                warehouseId?.let { warehouse ->
                    ItemViewScreen(navController = navController, itemId = id, warehouseId = warehouse)
                }
            }
        }
    }
}

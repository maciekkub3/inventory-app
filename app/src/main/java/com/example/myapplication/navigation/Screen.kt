package com.example.myapplication.navigation

const val ROUTE_SIGN_IN = "SIGN_IN"

const val ROUTE_OWNER_MENU = "OWNER_MENU"

const val ROUTE_OWNER_WAREHOUSE_MENU = "OWNER_WAREHOUSE_MENU"
const val ROUTE_OWNER_ADD_WAREHOUSE = "OWNER_ADD_WAREHOUSE"
const val ROUTE_OWNER_WAREHOUSE_VIEW = "OWNER_WAREHOUSE_VIEW"

const val ROUTE_OWNER_USERS_MENU = "OWNER_USERS_MENU"
const val ROUTE_OWNER_ADD_USERS = "OWNER_USERS_MENU"
const val ROUTE_OWNER_USERS_VIEW = "OWNER_USERS_VIEW"


const val ROUTE_WAREHOUSE_MENU = "WAREHOUSE_MENU"
const val ROUTE_MAIN_PAGE = "MAIN_PAGE"

const val ROUTE_SETTINGS = "SETTINGS"
const val ROUTE_EDIT_PROFILE = "EDIT_PROFILE"
const val ROUTE_LANGUAGE_SELECTION = "LANGUAGE_SELECTION"
const val ROUTE_CHANGE_PASSWORD = "CHANGE_PASSWORD"

const val ROUTE_QR_SCAN = "QR_SCAN"
const val ROUTE_SCANNED_PRODUCTS = "SCANNED_PRODUCTS"

const val ROUTE_ADD_PRODUCT = "ADD_PRODUCT"
const val ROUTE_ADD_NEW_PRODUCT = "ADD_NEW_PRODUCT"

const val ROUTE_HISTORY = "HISTORY"

const val ROUTE_WORKERS = "WORKERS"
const val ROUTE_WORKER_VIEW = "WORKER_VIEW"

const val ROUTE_REPORTS = "REPORTS"

const val ROUTE_INVENTORY = "INVENTORY"
const val ROUTE_ITEM_VIEW = "ITEM_VIEW"




sealed class Screen(
    val route: String
) {
    data object SignIn : Screen(route = ROUTE_SIGN_IN)
    data object MainPage : Screen(route = ROUTE_MAIN_PAGE)
    data object Settings : Screen(route = ROUTE_SETTINGS)
    data object EditProfile : Screen(route = ROUTE_EDIT_PROFILE)
    data object OwnerWarehouseMenu : Screen(route = ROUTE_OWNER_WAREHOUSE_MENU)
    data object OwnerAddWarehouse : Screen(route = ROUTE_OWNER_ADD_WAREHOUSE)
    data object OwnerWarehouseView : Screen(route = ROUTE_OWNER_WAREHOUSE_VIEW)
    data object OwnerUsersMenu : Screen(route = ROUTE_OWNER_USERS_MENU)
    data object OwnerAddUsers : Screen(route = ROUTE_OWNER_ADD_USERS)
    data object OwnerUsersView : Screen(route = ROUTE_OWNER_USERS_VIEW)
    data object LanguageSelection : Screen(route = ROUTE_LANGUAGE_SELECTION)
    data object ChangePassword : Screen(route = ROUTE_CHANGE_PASSWORD)
    data object QRScan : Screen(route = ROUTE_QR_SCAN)
    data object ScannedProducts : Screen(route = ROUTE_SCANNED_PRODUCTS)
    data object AddProduct : Screen(route = ROUTE_ADD_PRODUCT)
    data object AddNewProduct : Screen(route = ROUTE_ADD_NEW_PRODUCT)
    data object History : Screen(route = ROUTE_HISTORY)
    data object Workers : Screen(route = ROUTE_WORKERS)
    data object WorkerView : Screen(route = ROUTE_WORKER_VIEW)
    data object Reports : Screen(route = ROUTE_REPORTS)
    data object Inventory : Screen(route = ROUTE_INVENTORY)
    data object ItemView : Screen(route = ROUTE_ITEM_VIEW)
    data object OwnerMenu : Screen(route = ROUTE_OWNER_MENU)
    data object WarehouseMenu : Screen(route = ROUTE_WAREHOUSE_MENU)
}
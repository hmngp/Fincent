package com.example.fincent.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Dashboard : BottomNavItem(
        route = Screen.Dashboard.route,
        icon = Icons.Default.Home,
        label = "Dashboard"
    )
    
    object Expenses : BottomNavItem(
        route = Screen.Expenses.route,
        icon = Icons.Default.AttachMoney,
        label = "Expenses"
    )
    
    object Budget : BottomNavItem(
        route = Screen.Budget.route,
        icon = Icons.Default.AccountBalance,
        label = "Budget"
    )
    
    object Goals : BottomNavItem(
        route = Screen.Goals.route,
        icon = Icons.Default.Flag,
        label = "Goals"
    )
    
    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        icon = Icons.Default.Person,
        label = "Profile"
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Dashboard,
    BottomNavItem.Expenses,
    BottomNavItem.Budget,
    BottomNavItem.Goals,
    BottomNavItem.Profile
)


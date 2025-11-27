package com.example.fincent.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fincent.presentation.budget.AddBudgetScreen
import com.example.fincent.presentation.budget.BudgetListScreen
import com.example.fincent.presentation.dashboard.DashboardScreen
import com.example.fincent.presentation.expense.ExpenseListScreen
import com.example.fincent.presentation.goal.AddGoalScreen
import com.example.fincent.presentation.goal.GoalListScreen
import com.example.fincent.presentation.profile.ProfileScreen
import com.example.fincent.ui.navigation.BottomNavItem
import com.example.fincent.ui.navigation.Screen
import com.example.fincent.ui.navigation.bottomNavItems

@Composable
fun MainScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToAddExpense: () -> Unit,
    onNavigateToEditExpense: (String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(onNavigateToAddExpense = onNavigateToAddExpense)
            }

            composable(Screen.Expenses.route) {
                ExpenseListScreen(
                    onNavigateToAddExpense = onNavigateToAddExpense,
                    onNavigateToEditExpense = onNavigateToEditExpense
                )
            }

            composable(Screen.Budget.route) {
                BudgetListScreen(
                    onNavigateToAddBudget = {
                        navController.navigate(Screen.AddBudget.route)
                    }
                )
            }

            composable(Screen.Goals.route) {
                GoalListScreen(
                    onNavigateToAddGoal = {
                        navController.navigate(Screen.AddGoal.route)
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(onNavigateToLogin = onNavigateToLogin)
            }

            composable(Screen.AddBudget.route) {
                AddBudgetScreen(onNavigateBack = { navController.popBackStack() })
            }

            composable(Screen.AddGoal.route) {
                AddGoalScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


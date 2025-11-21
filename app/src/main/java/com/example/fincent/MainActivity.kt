package com.example.fincent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fincent.presentation.auth.AuthViewModel
import com.example.fincent.presentation.auth.LoginScreen
import com.example.fincent.presentation.auth.SignUpScreen
import com.example.fincent.presentation.expense.AddExpenseScreen
import com.example.fincent.presentation.main.MainScreen
import com.example.fincent.ui.navigation.Screen
import com.example.fincent.ui.theme.FincentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FincentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FincentApp()
                }
            }
        }
    }
}

@Composable
fun FincentApp(authViewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination = if (currentUser != null && currentUser!!.isEmailVerified) {
        "main"
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToDashboard = {
                    navController.navigate("main") {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToEmailVerification = {
                    // Show a message that email needs to be verified
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToEmailVerification = {
                    // Show email verification message
                    navController.popBackStack()
                }
            )
        }

        composable("main") {
            MainScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo("main") { inclusive = true }
                    }
                },
                onNavigateToAddExpense = {
                    navController.navigate(Screen.AddExpense.route)
                }
            )
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

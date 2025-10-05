package com.example.fincent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.fincent.ui.DashboardScreen
import com.example.fincent.ui.AddExpenseScreen
import com.example.fincent.ui.theme.FincentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FincentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display DashboardScreen by default
                    DashboardScreen(navController = androidx.navigation.compose.rememberNavController())
                    
                    // Uncomment the line below and comment the line above to capture AddExpenseScreen screenshot
                    // AddExpenseScreen(navController = androidx.navigation.compose.rememberNavController())
                }
            }
        }
    }
}
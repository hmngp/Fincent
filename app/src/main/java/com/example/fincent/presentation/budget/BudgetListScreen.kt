package com.example.fincent.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincent.presentation.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetListScreen(
    onNavigateToAddBudget: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val budgets by budgetViewModel.budgets.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            budgetViewModel.loadBudgets(user.uid)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Budgets") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddBudget) {
                Icon(Icons.Filled.Add, "Add Budget")
            }
        }
    ) { padding ->
        if (budgets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No budgets yet. Create your first budget!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(budgets) { budget ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    budget.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (budget.isActive) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    ) {
                                        Text(
                                            "Active",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            LinearProgressIndicator(
                                progress = if (budget.totalAmount > 0) {
                                    (budget.spentAmount / budget.totalAmount).toFloat().coerceIn(0f, 1f)
                                } else {
                                    0f
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Spent: $${"%.2f".format(budget.spentAmount)}",
                                    fontSize = 14.sp
                                )
                                Text(
                                    "Total: $${"%.2f".format(budget.totalAmount)}",
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


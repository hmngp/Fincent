package com.example.fincent.presentation.dashboard

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
import com.example.fincent.presentation.expense.ExpenseViewModel
import com.example.fincent.presentation.budget.BudgetViewModel
import com.example.fincent.presentation.bill.BillViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddExpense: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    billViewModel: BillViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val expenses by expenseViewModel.expenses.collectAsState()
    val budgets by budgetViewModel.activeBudgets.collectAsState()
    val bills by billViewModel.unpaidBills.collectAsState()

    LaunchedEffect(currentUser) {
        val userId = currentUser?.uid ?: "demo-user"
        expenseViewModel.loadExpenses(userId)
        budgetViewModel.loadActiveBudgets(userId)
        billViewModel.loadUnpaidBills(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Fincent",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddExpense,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Filled.Add, "Add Expense")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Weekly Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Weekly Summary",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Total Spent", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                val totalExpense = expenses
                                    .filter { it.date >= getStartOfWeek() }
                                    .sumOf { it.amount }
                                Text(
                                    "$${"%.2f".format(totalExpense)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Budget Left", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                val totalBudget = budgets.sumOf { it.totalAmount }
                                val totalSpentMonth = expenses
                                    .filter { it.date >= getStartOfMonth() }
                                    .sumOf { it.amount }
                                val budgetLeft = (totalBudget - totalSpentMonth).coerceAtLeast(0.0)
                                Text(
                                    "$${"%.2f".format(budgetLeft)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }

            // Upcoming Bills
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Upcoming Bills",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        if (bills.isEmpty()) {
                            Text("No upcoming bills", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            bills.take(3).forEach { bill ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(bill.name, fontWeight = FontWeight.Medium)
                                        Text(
                                            "Due: ${formatDate(bill.dueDate)}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        "$${"%.2f".format(bill.amount)}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Recent Transactions
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Recent Transactions",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (expenses.isEmpty()) {
                            Text("No recent transactions", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            expenses.take(5).forEach { expense ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            expense.description.ifEmpty { "Expense" },
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            formatDate(expense.date),
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        "-$${"%.2f".format(expense.amount)}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

private fun getStartOfMonth(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

private fun getStartOfWeek(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}



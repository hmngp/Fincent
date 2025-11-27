package com.example.fincent.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincent.domain.model.Expense
import com.example.fincent.domain.model.ExpenseCategory
import com.example.fincent.presentation.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onNavigateBack: () -> Unit,
    expenseId: String? = null,
    authViewModel: AuthViewModel = hiltViewModel(),
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    budgetViewModel: com.example.fincent.presentation.budget.BudgetViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ExpenseCategory.OTHER) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var expanded by remember { mutableStateOf(false) }
    
    // New features state
    var isDeductFromBudget by remember { mutableStateOf(true) }
    var showSplitDialog by remember { mutableStateOf(false) }
    var splitWithCount by remember { mutableStateOf("") }

    val context = androidx.compose.ui.platform.LocalContext.current
    val calendar = java.util.Calendar.getInstance()
    calendar.timeInMillis = selectedDate

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        },
        calendar.get(java.util.Calendar.YEAR),
        calendar.get(java.util.Calendar.MONTH),
        calendar.get(java.util.Calendar.DAY_OF_MONTH)
    )

    val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())

    val currentUser by authViewModel.currentUser.collectAsState()
    val activeBudgets by budgetViewModel.activeBudgets.collectAsState()

    LaunchedEffect(currentUser, expenseId) {
        val userId = currentUser?.uid ?: "demo-user"
        budgetViewModel.loadBudgets(userId)
        budgetViewModel.loadActiveBudgets(userId)
        
        if (expenseId != null) {
            val expense = expenseViewModel.getExpenseById(expenseId)
            expense?.let {
                amount = it.amount.toString()
                description = it.description
                selectedCategory = it.category
                selectedDate = it.date
                // Disable deduct from budget when editing to avoid double deduction logic complexity for now
                isDeductFromBudget = false 
            }
        }
    }

    if (showSplitDialog) {
        AlertDialog(
            onDismissRequest = { showSplitDialog = false },
            title = { Text("Split with Friends") },
            text = {
                Column {
                    Text("Enter number of people (including you):")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = splitWithCount,
                        onValueChange = { splitWithCount = it },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    val totalAmount = amount.toDoubleOrNull() ?: 0.0
                    val count = splitWithCount.toIntOrNull() ?: 1
                    
                    if (totalAmount > 0 && count > 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        val perPerson = totalAmount / count
                        val friendsCount = count - 1
                        val collectAmount = perPerson * friendsCount
                        
                        Text(
                            "You paid: $${"%.2f".format(totalAmount)}",
                            fontWeight = FontWeight.Bold
                        )
                        Text("Your share: $${"%.2f".format(perPerson)}")
                        Text(
                            "Collect $${"%.2f".format(perPerson)} from each of your $friendsCount friends.",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Total to collect: $${"%.2f".format(collectAmount)}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSplitDialog = false }) {
                    Text("Done")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (expenseId != null) "Edit Expense" else "Add Expense") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ExpenseCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateFormat.format(java.util.Date(selectedDate)),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Date") },
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Filled.DateRange, "Select Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { datePickerDialog.show() }
                )
            }

            if (activeBudgets.isNotEmpty() && expenseId == null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isDeductFromBudget,
                        onCheckedChange = { isDeductFromBudget = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Spend from Budget")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val expenseAmount = amount.toDoubleOrNull() ?: 0.0
                    if (expenseAmount > 0) {
                        val userId = currentUser?.uid ?: "demo-user"
                        val expense = Expense(
                            id = expenseId ?: "",
                            userId = userId,
                            amount = expenseAmount,
                            description = description.ifBlank { "Expense" },
                            category = selectedCategory,
                            date = selectedDate
                        )
                        
                        if (expenseId != null) {
                            expenseViewModel.updateExpense(expense)
                        } else {
                            expenseViewModel.addExpense(expense)
                            if (isDeductFromBudget && activeBudgets.isNotEmpty()) {
                                budgetViewModel.deductExpenseFromBudget(selectedCategory, expenseAmount)
                            }
                        }
                        
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(if (expenseId != null) "Update Expense" else "Add Expense", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = { showSplitDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Split with Friends", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}


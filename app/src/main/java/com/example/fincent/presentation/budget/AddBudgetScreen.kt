package com.example.fincent.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincent.domain.model.Budget
import com.example.fincent.domain.model.BudgetCategory
import com.example.fincent.domain.model.BudgetType
import com.example.fincent.presentation.auth.AuthViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetScreen(
    onNavigateBack: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(BudgetCategory.GENERAL) }
    var selectedType by remember { mutableStateOf(BudgetType.MONTHLY) }
    
    var categoryExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Budget") },
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Budget Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Total Amount") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    BudgetCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // Type Dropdown
            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedType.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Budget Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(typeExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false }
                ) {
                    BudgetType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                selectedType = type
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val context = androidx.compose.ui.platform.LocalContext.current
            Button(
                onClick = {
                    val budgetAmount = amount.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank() && budgetAmount > 0) {
                        val userId = currentUser?.uid ?: "demo-user"
                        
                        // Calculate dates based on type
                        val calendar = Calendar.getInstance()
                        val startDate = calendar.timeInMillis
                        
                        when (selectedType) {
                            BudgetType.WEEKLY -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
                            BudgetType.MONTHLY -> calendar.add(Calendar.MONTH, 1)
                            BudgetType.SEMESTER -> calendar.add(Calendar.MONTH, 6)
                            BudgetType.YEARLY -> calendar.add(Calendar.YEAR, 1)
                        }
                        val endDate = calendar.timeInMillis

                        val budget = Budget(
                            userId = userId,
                            name = name,
                            totalAmount = budgetAmount,
                            spentAmount = 0.0,
                            category = selectedCategory,
                            budgetType = selectedType,
                            startDate = startDate,
                            endDate = endDate,
                            isActive = true
                        )
                        budgetViewModel.addBudget(budget)
                        onNavigateBack()
                    } else {
                        // Show error if validation fails
                        android.widget.Toast.makeText(
                            context, 
                            "Please enter a valid name and amount", 
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Create Budget", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

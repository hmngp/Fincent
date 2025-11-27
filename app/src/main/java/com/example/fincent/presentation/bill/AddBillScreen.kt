package com.example.fincent.presentation.bill

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincent.domain.model.Bill
import com.example.fincent.domain.model.ExpenseCategory
import com.example.fincent.presentation.auth.AuthViewModel
import java.util.Calendar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillScreen(
    onNavigateBack: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    billViewModel: BillViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var reminderDays by remember { mutableStateOf("3") }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = selectedDate

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Bill") },
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
                label = { Text("Bill Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                prefix = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateFormat.format(java.util.Date(selectedDate)),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Due Date") },
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

            OutlinedTextField(
                value = reminderDays,
                onValueChange = { reminderDays = it },
                label = { Text("Remind me (days before)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val billAmount = amount.toDoubleOrNull() ?: 0.0
                    val days = reminderDays.toIntOrNull() ?: 3
                    
                    if (name.isNotBlank() && billAmount > 0) {
                        val userId = currentUser?.uid ?: "demo-user"
                        val bill = Bill(
                            id = UUID.randomUUID().toString(),
                            userId = userId,
                            name = name,
                            amount = billAmount,
                            dueDate = selectedDate,
                            category = ExpenseCategory.BILLS,
                            isPaid = false,
                            reminderDays = days
                        )
                        billViewModel.addBill(bill)
                        onNavigateBack()
                    } else {
                         android.widget.Toast.makeText(
                            context, 
                            "Please enter valid details", 
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Add Bill", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

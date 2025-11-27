package com.example.fincent.presentation.bill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincent.presentation.auth.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillListScreen(
    onNavigateToAddBill: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    billViewModel: BillViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val bills by billViewModel.bills.collectAsState()

    LaunchedEffect(currentUser) {
        val userId = currentUser?.uid ?: "demo-user"
        billViewModel.loadBills(userId)
    }

    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bills") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddBill,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Filled.Add, "Add Bill")
            }
        }
    ) { padding ->
        if (bills.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No bills tracked yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bills) { bill ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (bill.isPaid) 
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) 
                            else 
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    bill.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    style = if (bill.isPaid) androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough) else androidx.compose.ui.text.TextStyle()
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Due: ${dateFormat.format(java.util.Date(bill.dueDate))}",
                                    fontSize = 12.sp,
                                    color = if (bill.dueDate < System.currentTimeMillis() && !bill.isPaid) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "$${"%.2f".format(bill.amount)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = bill.isPaid,
                                        onCheckedChange = { isChecked ->
                                            billViewModel.updateBill(bill.copy(isPaid = isChecked))
                                        }
                                    )
                                    Text(if (bill.isPaid) "Paid" else "Unpaid", fontSize = 12.sp)
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    
                                    IconButton(
                                        onClick = { billViewModel.deleteBill(bill) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete, 
                                            contentDescription = "Delete Bill",
                                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

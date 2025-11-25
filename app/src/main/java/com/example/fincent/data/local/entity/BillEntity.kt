package com.example.fincent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fincent.domain.model.ExpenseCategory

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val amount: Double,
    val dueDate: Long,
    val category: ExpenseCategory = ExpenseCategory.BILLS,
    val isPaid: Boolean = false,
    val reminderDays: Int = 3
)

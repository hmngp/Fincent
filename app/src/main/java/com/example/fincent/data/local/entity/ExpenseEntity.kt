package com.example.fincent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fincent.data.local.converter.Converters
import com.example.fincent.domain.model.*

@Entity(tableName = "expenses")
@TypeConverters(Converters::class)
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val amount: Double,
    val category: ExpenseCategory,
    val description: String,
    val date: Long,
    val isRecurring: Boolean,
    val recurringType: RecurringType?,
    val receiptImageUrl: String?,
    val isSplit: Boolean,
    val splitDetails: SplitExpense?,
    val tags: List<String>
)

fun ExpenseEntity.toDomain() = Expense(
    id = id,
    userId = userId,
    amount = amount,
    category = category,
    description = description,
    date = date,
    isRecurring = isRecurring,
    recurringType = recurringType,
    receiptImageUrl = receiptImageUrl,
    isSplit = isSplit,
    splitDetails = splitDetails,
    tags = tags
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    userId = userId,
    amount = amount,
    category = category,
    description = description,
    date = date,
    isRecurring = isRecurring,
    recurringType = recurringType,
    receiptImageUrl = receiptImageUrl,
    isSplit = isSplit,
    splitDetails = splitDetails,
    tags = tags
)


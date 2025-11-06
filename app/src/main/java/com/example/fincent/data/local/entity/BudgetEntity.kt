package com.example.fincent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fincent.data.local.converter.Converters
import com.example.fincent.domain.model.Budget
import com.example.fincent.domain.model.BudgetCategory
import com.example.fincent.domain.model.BudgetType

@Entity(tableName = "budgets")
@TypeConverters(Converters::class)
data class BudgetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val totalAmount: Double,
    val spentAmount: Double,
    val category: BudgetCategory,
    val startDate: Long,
    val endDate: Long,
    val isActive: Boolean,
    val budgetType: BudgetType
)

fun BudgetEntity.toDomain() = Budget(
    id, userId, name, totalAmount, spentAmount, category, startDate, endDate, isActive, budgetType
)

fun Budget.toEntity() = BudgetEntity(
    id, userId, name, totalAmount, spentAmount, category, startDate, endDate, isActive, budgetType
)


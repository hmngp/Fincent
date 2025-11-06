package com.example.fincent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fincent.data.local.converter.Converters
import com.example.fincent.domain.model.FinancialGoal
import com.example.fincent.domain.model.GoalCategory
import com.example.fincent.domain.model.Priority

@Entity(tableName = "goals")
@TypeConverters(Converters::class)
data class GoalEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadline: Long,
    val category: GoalCategory,
    val priority: Priority,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long
)

fun GoalEntity.toDomain() = FinancialGoal(
    id, userId, name, targetAmount, currentAmount, deadline, category, priority, description, isCompleted, createdAt
)

fun FinancialGoal.toEntity() = GoalEntity(
    id, userId, name, targetAmount, currentAmount, deadline, category, priority, description, isCompleted, createdAt
)


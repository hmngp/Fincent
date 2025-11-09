package com.example.fincent.data.repository

import com.example.fincent.data.local.dao.ExpenseDao
import com.example.fincent.data.local.entity.toEntity
import com.example.fincent.data.local.entity.toDomain
import com.example.fincent.domain.model.Expense
import com.example.fincent.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    fun getAllExpenses(userId: String): Flow<List<Expense>> {
        return expenseDao.getAllExpenses(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getExpensesByDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesByDateRange(userId, startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getExpensesByCategory(userId: String, category: ExpenseCategory): Flow<List<Expense>> {
        return expenseDao.getExpensesByCategory(userId, category).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getExpenseById(id: String): Expense? {
        return expenseDao.getExpenseById(id)?.toDomain()
    }

    suspend fun insertExpense(expense: Expense) {
        val expenseWithId = if (expense.id.isEmpty()) {
            expense.copy(id = UUID.randomUUID().toString())
        } else expense

        expenseDao.insertExpense(expenseWithId.toEntity())
    }

    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense.toEntity())
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }

    suspend fun getTotalExpenseInRange(userId: String, startDate: Long, endDate: Long): Double {
        return expenseDao.getTotalExpenseInRange(userId, startDate, endDate) ?: 0.0
    }

    suspend fun getTotalExpenseByCategory(
        userId: String,
        category: ExpenseCategory,
        startDate: Long,
        endDate: Long
    ): Double {
        return expenseDao.getTotalExpenseByCategory(userId, category, startDate, endDate) ?: 0.0
    }
}


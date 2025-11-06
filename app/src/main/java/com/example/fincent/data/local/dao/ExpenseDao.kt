package com.example.fincent.data.local.dao

import androidx.room.*
import com.example.fincent.data.local.entity.ExpenseEntity
import com.example.fincent.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getAllExpenses(userId: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND category = :category ORDER BY date DESC")
    fun getExpensesByCategory(userId: String, category: ExpenseCategory): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: String): ExpenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenses(expenses: List<ExpenseEntity>)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE userId = :userId")
    suspend fun deleteAllExpenses(userId: String)

    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalExpenseInRange(userId: String, startDate: Long, endDate: Long): Double?

    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND category = :category AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalExpenseByCategory(userId: String, category: ExpenseCategory, startDate: Long, endDate: Long): Double?
}


package com.example.fincent.data.local.dao

import androidx.room.*
import com.example.fincent.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE userId = :userId ORDER BY startDate DESC")
    fun getAllBudgets(userId: String): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE userId = :userId AND isActive = 1")
    fun getActiveBudgets(userId: String): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: String): BudgetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Update
    suspend fun updateBudget(budget: BudgetEntity)

    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)
}


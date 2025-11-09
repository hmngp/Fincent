package com.example.fincent.data.repository

import com.example.fincent.data.local.dao.BudgetDao
import com.example.fincent.data.local.entity.toEntity
import com.example.fincent.data.local.entity.toDomain
import com.example.fincent.domain.model.Budget
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {

    fun getAllBudgets(userId: String): Flow<List<Budget>> {
        return budgetDao.getAllBudgets(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getActiveBudgets(userId: String): Flow<List<Budget>> {
        return budgetDao.getActiveBudgets(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getBudgetById(id: String): Budget? {
        return budgetDao.getBudgetById(id)?.toDomain()
    }

    suspend fun insertBudget(budget: Budget) {
        val budgetWithId = if (budget.id.isEmpty()) {
            budget.copy(id = UUID.randomUUID().toString())
        } else budget

        budgetDao.insertBudget(budgetWithId.toEntity())
    }

    suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget.toEntity())
    }

    suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget.toEntity())
    }
}


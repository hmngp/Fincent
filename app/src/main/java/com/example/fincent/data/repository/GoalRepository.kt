package com.example.fincent.data.repository

import com.example.fincent.data.local.dao.GoalDao
import com.example.fincent.data.local.entity.toEntity
import com.example.fincent.data.local.entity.toDomain
import com.example.fincent.domain.model.FinancialGoal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val goalDao: GoalDao
) {

    fun getAllGoals(userId: String): Flow<List<FinancialGoal>> {
        return goalDao.getAllGoals(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getActiveGoals(userId: String): Flow<List<FinancialGoal>> {
        return goalDao.getActiveGoals(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getGoalById(id: String): FinancialGoal? {
        return goalDao.getGoalById(id)?.toDomain()
    }

    suspend fun insertGoal(goal: FinancialGoal) {
        val goalWithId = if (goal.id.isEmpty()) {
            goal.copy(id = UUID.randomUUID().toString())
        } else goal

        goalDao.insertGoal(goalWithId.toEntity())
    }

    suspend fun updateGoal(goal: FinancialGoal) {
        goalDao.updateGoal(goal.toEntity())
    }

    suspend fun deleteGoal(goal: FinancialGoal) {
        goalDao.deleteGoal(goal.toEntity())
    }
}


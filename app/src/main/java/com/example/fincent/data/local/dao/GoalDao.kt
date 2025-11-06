package com.example.fincent.data.local.dao

import androidx.room.*
import com.example.fincent.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals WHERE userId = :userId ORDER BY deadline ASC")
    fun getAllGoals(userId: String): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE userId = :userId AND isCompleted = 0 ORDER BY priority DESC, deadline ASC")
    fun getActiveGoals(userId: String): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: String): GoalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)
}


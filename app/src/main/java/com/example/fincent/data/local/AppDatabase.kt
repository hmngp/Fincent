package com.example.fincent.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fincent.data.local.converter.Converters
import com.example.fincent.data.local.dao.*
import com.example.fincent.data.local.entity.*

@Database(
    entities = [
        ExpenseEntity::class,
        BudgetEntity::class,
        GoalEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun goalDao(): GoalDao

    companion object {
        const val DATABASE_NAME = "fincent_database"
    }
}


package com.example.fincent.util

import android.content.Context
import com.example.fincent.domain.model.Budget
import com.example.fincent.domain.model.Expense
import com.example.fincent.domain.model.FinancialGoal
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataExporter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    data class ExportData(
        val expenses: List<Expense>,
        val budgets: List<Budget>,
        val goals: List<FinancialGoal>,
        val exportDate: Long = System.currentTimeMillis()
    )

    suspend fun exportToJson(
        expenses: List<Expense>,
        budgets: List<Budget>,
        goals: List<FinancialGoal>
    ): Result<String> {
        return try {
            val exportData = ExportData(expenses, budgets, goals)
            val json = gson.toJson(exportData)
            
            val fileName = "fincent_backup_${System.currentTimeMillis()}.json"
            val file = File(context.getExternalFilesDir(null), fileName)
            file.writeText(json)
            
            Result.success(file.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun importFromJson(filePath: String): Result<ExportData> {
        return try {
            val file = File(filePath)
            val json = file.readText()
            val exportData = gson.fromJson(json, ExportData::class.java)
            Result.success(exportData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun generateCsvReport(expenses: List<Expense>): String {
        val sb = StringBuilder()
        sb.appendLine("Date,Description,Category,Amount")
        
        expenses.forEach { expense ->
            sb.appendLine("${expense.date},${expense.description},${expense.category},${expense.amount}")
        }
        
        return sb.toString()
    }
}


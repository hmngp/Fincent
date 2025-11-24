package com.example.fincent.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fincent.FincentApp
import com.example.fincent.R

class WeeklyReportWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // TODO: Calculate weekly expenses from database
            val totalExpenses = inputData.getDouble("TOTAL_EXPENSES", 0.0)
            
            showNotification(totalExpenses)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(totalExpenses: Double) {
        val notification = NotificationCompat.Builder(applicationContext, FincentApp.CHANNEL_REPORTS_ID)
            .setContentTitle("Weekly Financial Report")
            .setContentText("You spent $${"%.2f".format(totalExpenses)} this week")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(999, notification)
    }
}


package com.example.fincent.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fincent.FincentApp
import com.example.fincent.R

class BillReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val billName = inputData.getString("BILL_NAME") ?: "Bill"
            val amount = inputData.getDouble("AMOUNT", 0.0)
            
            showNotification(billName, amount)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(billName: String, amount: Double) {
        val notification = NotificationCompat.Builder(applicationContext, FincentApp.CHANNEL_BILLS_ID)
            .setContentTitle("Bill Reminder")
            .setContentText("$billName: $${"%.2f".format(amount)} is due soon")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(billName.hashCode(), notification)
    }
}


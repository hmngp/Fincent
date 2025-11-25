package com.example.fincent

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FincentApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_BILLS_ID,
                    "Bill Reminders",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifications for upcoming bills"
                },
                NotificationChannel(
                    CHANNEL_BUDGET_ID,
                    "Budget Alerts",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifications for budget limits and alerts"
                },
                NotificationChannel(
                    CHANNEL_GOALS_ID,
                    "Goal Progress",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Notifications for financial goal progress"
                },
                NotificationChannel(
                    CHANNEL_REPORTS_ID,
                    "Financial Reports",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Weekly and monthly financial summaries"
                }
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.let { manager ->
                channels.forEach { manager.createNotificationChannel(it) }
            }
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }

    companion object {
        const val CHANNEL_BILLS_ID = "bills_channel"
        const val CHANNEL_BUDGET_ID = "budget_channel"
        const val CHANNEL_GOALS_ID = "goals_channel"
        const val CHANNEL_REPORTS_ID = "reports_channel"
    }
}


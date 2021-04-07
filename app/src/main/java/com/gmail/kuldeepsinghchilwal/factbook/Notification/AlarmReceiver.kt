package com.gmail.kuldeepsinghchilwal.factbook.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.gmail.kuldeepsinghchilwal.factbook.Notification.sendNotification
import com.gmail.kuldeepsinghchilwal.factbook.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            context.getText(R.string.daily_fact_notification_text).toString(),
            context
        )

    }
}
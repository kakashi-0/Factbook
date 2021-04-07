package com.gmail.kuldeepsinghchilwal.factbook.Notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.gmail.kuldeepsinghchilwal.factbook.MainActivity
import com.gmail.kuldeepsinghchilwal.factbook.R

// Notification ID.
private const val NOTIFICATION_ID = 0


/**
 * Builds and delivers the notification.
 *
 * @param applicationContext, application context.
 */

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.daily_fact_notification_channel_id)
    )

        //setting  notification icon, title and body text
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle(applicationContext.getString(R.string.notification_app_name))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
    //Deliever the notification
    notify(NOTIFICATION_ID, builder.build())

}
package com.example.mathsolver.workers

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.mathsolver.utils.CHANNEL_ID
import com.example.mathsolver.utils.FOREGROUND_SERVICE_ID

class BackgroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        //start the service
        startForeground(FOREGROUND_SERVICE_ID, createNotification())
    }

    private fun createNotification(): Notification {
        //minimal notification to run the background service
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()

        return notification
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // When your work is done, remove the service from the foreground state
        stopForeground(true)

        // Stop the service
        stopSelf()
        return START_NOT_STICKY
    }
}

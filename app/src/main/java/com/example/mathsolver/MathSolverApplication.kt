package com.example.mathsolver

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.mathsolver.utils.CHANNEL_ID
import com.example.mathsolver.workers.BackgroundService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


// You can include application-level setup here if needed
var appContext: MathSolverApplication? = null

@HiltAndroidApp
@RequiresApi(Build.VERSION_CODES.O)
class MathSolverApplication : Application() {

    companion object {
        fun getAppContext(): MathSolverApplication? {
            return appContext
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        // Initialize any global configurations or dependencies here
        appContext = this
        val lifecycleCallbacks = MyActivityLifecycleCallbacks()
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
        createNotificationChannel()
        WorkManager.initialize(appContext!!, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }

    fun createNotificationChannel() {
        val channelId = CHANNEL_ID
        val channelName = "Background-WorkManager"
        val importance = NotificationManager.IMPORTANCE_LOW // Adjust importance as needed

        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

//    override fun startService(service: Intent?): ComponentName? {
//        return super.startService(service)
//    }
//
//    override fun stopService(name: Intent?): Boolean {
//        return super.stopService(name)
//    }

}

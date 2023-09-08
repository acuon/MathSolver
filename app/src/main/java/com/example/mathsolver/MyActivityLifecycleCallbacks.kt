package com.example.mathsolver

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.mathsolver.utils.startBackgroundService
import com.example.mathsolver.utils.stopBackgroundService

class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        // The app is coming back to the foreground, stop the background service here
        Log.d("WorkManagerMathSolver", "BackgroundService Stopped")
        stopBackgroundService(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        // The app is going into the background, start the background service here
        Log.d("WorkManagerMathSolver", "BackgroundService Started")
        startBackgroundService(activity)
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}

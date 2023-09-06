package com.example.mathsolver

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MathSolverApplication : Application() {

    // You can include application-level setup here if needed

    override fun onCreate() {
        super.onCreate()

        // Initialize any global configurations or dependencies here
    }
}

package com.example.mathsolver.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mathsolver.workers.BackgroundService
import java.util.Date


fun Context.showToast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
fun Long?.getDate(): String? {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return this?.let { dateFormat.format(Date(it)) }
}

fun String.convertToList(): List<String> {
    val allExpressions = split("\n").filter { it.isNotEmpty() }
    return if (allExpressions.size > 50) {
        allExpressions.subList(0, 50)
    } else {
        allExpressions
    }
}

fun startBackgroundService(context: Context) {
    val intent = Intent(context, BackgroundService::class.java)
    ContextCompat.startForegroundService(context, intent)
}

fun stopBackgroundService(context: Context) {
    val intent = Intent(context, BackgroundService::class.java)
    context.stopService(intent)
}

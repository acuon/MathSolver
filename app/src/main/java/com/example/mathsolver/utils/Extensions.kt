package com.example.mathsolver.utils

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.Toast
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

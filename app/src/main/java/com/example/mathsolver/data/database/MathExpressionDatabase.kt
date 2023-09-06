package com.example.mathsolver.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MathExpressionHistoryEntity::class], version = 2, exportSchema = false)
abstract class MathExpressionDatabase : RoomDatabase() {
    abstract fun mathExpressionDao(): MathExpressionDao
}

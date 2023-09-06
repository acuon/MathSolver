package com.example.mathsolver.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MathExpressionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpression(expression: MathExpressionHistoryEntity)

    @Query("SELECT * FROM math_expression_history ORDER BY timestamp DESC")
    fun getAllExpressions(): Flow<List<MathExpressionHistoryEntity>>
}

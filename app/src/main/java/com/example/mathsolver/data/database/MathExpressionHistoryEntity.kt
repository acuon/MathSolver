package com.example.mathsolver.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "math_expression_history")
data class MathExpressionHistoryEntity constructor(
    @ColumnInfo(name = "input")
    var expression: String? = null,
    @ColumnInfo(name = "result")
    var result: String? = null,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long? = System.currentTimeMillis()
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @Ignore
    constructor(input: String?, result: String?) : this(input, result, 0)

    @Ignore
    constructor() : this(null, null, null)
}


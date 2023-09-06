package com.example.mathsolver.data.repository

import com.example.mathsolver.BuildConfig
import com.example.mathsolver.data.database.MathExpressionDao
import com.example.mathsolver.data.database.MathExpressionHistoryEntity
import com.example.mathsolver.data.network.WolframAlphaService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class MathExpressionRepository @Inject constructor(
    private val wolframAlphaService: WolframAlphaService,
    private val mathExpressionDao: MathExpressionDao
) {
    suspend fun evaluateExpression(expression: String): String? {
        val response = wolframAlphaService.evaluateExpression(BuildConfig.APP_ID, expression)
        return response.body()
    }

    fun insertExpression(expressionEntity: MathExpressionHistoryEntity) {
        mathExpressionDao.insertExpression(expressionEntity)
    }

    fun getAllExpressions(): Flow<List<MathExpressionHistoryEntity>> {
        return mathExpressionDao.getAllExpressions()
    }
}

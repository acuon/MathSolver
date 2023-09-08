package com.example.mathsolver.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    // LiveData to observe the latest expression
    private val latestExpressionLiveData: MutableLiveData<List<MathExpressionHistoryEntity?>> = MutableLiveData()

    // Function to get the LiveData
    fun getLatestExpressionLiveData(): LiveData<List<MathExpressionHistoryEntity?>> {
        return latestExpressionLiveData
    }

    // Function to update the LiveData
    fun updateLatestExpressionLiveData(expressions: List<MathExpressionHistoryEntity?>) {
        Log.d("WorkManagerMathSolver", "expressions $expressions")
        latestExpressionLiveData.postValue(expressions)
    }

    suspend fun evaluateExpression(expression: String): String? {
        val response = wolframAlphaService.evaluateExpression(BuildConfig.APP_ID, expression)
        return response.body()
    }

    fun insertExpression(expressionEntity: MathExpressionHistoryEntity) {
        Log.d("WorkManagerMathSolver", "success $expressionEntity")
        mathExpressionDao.insertExpression(expressionEntity)
    }

    fun getAllExpressions(): Flow<List<MathExpressionHistoryEntity>> {
        return mathExpressionDao.getAllExpressions()
    }
}

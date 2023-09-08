package com.example.mathsolver.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.example.mathsolver.data.database.MathExpressionHistoryEntity
import com.example.mathsolver.data.repository.MathExpressionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.CoroutineWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MathEvaluationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MathExpressionRepository,
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        Log.d("WorkManagerMathSolver", "started")
        val expressions = inputData.getStringArray("expressions")
        Log.d("WorkManagerMathSolver", "$expressions")
        if (expressions.isNullOrEmpty()) {
            return Result.failure()
        }

        try {
            val results = mutableListOf<MathExpressionHistoryEntity>()

            for (expression in expressions) {
                val result = withContext(Dispatchers.IO) {
                    MathExpressionHistoryEntity(
                        expression = expression,
                        result = repository.evaluateExpression(expression) ?: "No result available"
                    )
                }
                results.add(result)

                // Insert the result into the Room database
                repository.insertExpression(result)

            }
            // Update LiveData in the repository with the latest result
            repository.updateLatestExpressionLiveData(results)

            return Result.success()
        } catch (e: Exception) {
            // Handle errors, e.g., network issues or API errors
            return Result.failure()
        }
    }
}

package com.example.mathsolver.ui.view

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathsolver.data.database.MathExpressionHistoryEntity
import com.example.mathsolver.data.repository.MathExpressionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MathExpressionRepository
) : ViewModel() {

    private val _expressionResult = MutableStateFlow<MathExpressionHistoryEntity?>(null)
    val expressionResult: StateFlow<MathExpressionHistoryEntity?>
        get() = _expressionResult

    private val _allExpressions = MutableStateFlow<List<MathExpressionHistoryEntity>>(emptyList())
    val allExpressions: StateFlow<List<MathExpressionHistoryEntity>>
        get() = _allExpressions

    val expressionsList: StateFlow<List<MathExpressionHistoryEntity>> =
        repository.getAllExpressions()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun updateExpression(expressionEntity: MathExpressionHistoryEntity) {
        _expressionResult.value = expressionEntity
    }


    suspend fun evaluateMultipleExpressions(expressions: List<String>) {
        try {
            val results = mutableListOf<MathExpressionHistoryEntity>()
            val deferredInsertions = mutableListOf<Deferred<Unit>>()

            for (expression in expressions) {
                val result = withContext(Dispatchers.IO) {
                    MathExpressionHistoryEntity(
                        expression = expression,
                        result = repository.evaluateExpression(expression) ?: "No result available"
                    )
                }
                results.add(result)

                // Start asynchronous database insertion
                val deferredInsertion = viewModelScope.async(Dispatchers.IO) {
                    repository.insertExpression(result)
                }
                deferredInsertions.add(deferredInsertion)
            }

            // Wait for all insertions to complete
            deferredInsertions.awaitAll()

            // Notify the UI with the list of results
            _expressionResult.value = results.lastOrNull()
            _allExpressions.value = results
            Log.d("TAG", "Success $results")
        } catch (e: Exception) {
            // Handle errors, e.g., network issues or API errors
            val errorMessage = "Error: ${e.message}"
            _expressionResult.value =
                MathExpressionHistoryEntity(result = errorMessage)
            Log.d("TAG", "Failure $errorMessage")
        }
    }

    fun evaluateExpression(expression: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    MathExpressionHistoryEntity(
                        expression = expression,
                        result = repository.evaluateExpression(expression) ?: "No result available"
                    )
                }

                withContext(Dispatchers.IO) {
                    repository.insertExpression(result)
                }
                _expressionResult.value = result
                Log.d("TAG", "Success $result")
            } catch (e: Exception) {
                // Handle errors, e.g., network issues or API errors
                _expressionResult.value =
                    MathExpressionHistoryEntity(result = "Error: ${e.message}")
                Log.d("TAG", "Failure ${e.message}")
            }
        }
    }


    // Function to insert an expression into the Room database
    private fun insertExpression(expression: MathExpressionHistoryEntity) {
        repository.insertExpression(expression)
    }
}

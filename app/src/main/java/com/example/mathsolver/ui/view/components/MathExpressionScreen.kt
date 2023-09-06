package com.example.mathsolver.ui.view.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.mathsolver.ui.view.MainViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mathsolver.utils.Option
import com.example.mathsolver.utils.convertToList
import com.example.mathsolver.utils.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathExpressionScreen(viewModel: MainViewModel, context: Context, padding: PaddingValues) {

    val result by viewModel.expressionResult.collectAsState()
    val results by viewModel.allExpressions.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    var userInput by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(Option.SINGLE_EXPRESSION) }
    var isApiCallInProgress by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Radio buttons to select the evaluation mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            RadioButton(
                selected = selectedOption == Option.SINGLE_EXPRESSION,
                onClick = {
                    selectedOption = Option.SINGLE_EXPRESSION
                    userInput = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                "Single Expression",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedOption == Option.MULTIPLE_EXPRESSIONS,
                onClick = {
                    selectedOption = Option.MULTIPLE_EXPRESSIONS
                    userInput = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                "Multiple Expressions",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        TextField(
            value = userInput,
            onValueChange = { newValue ->
                userInput = newValue
            },
            label = { Text("Enter expression(s)") }
        )

        Button(
            onClick = {
                val expression = userInput
                Log.d("TAG", "After Click $expression")
                coroutineScope.launch {
                    isApiCallInProgress = true // Start API call
                    when (selectedOption) {
                        Option.SINGLE_EXPRESSION -> {
                            val singleExpression = expression.convertToList()[0]
                            viewModel.evaluateExpression(singleExpression)
                        }
                        Option.MULTIPLE_EXPRESSIONS -> viewModel.evaluateMultipleExpressions(expression.convertToList())
                    }
                    userInput = ""
                    isApiCallInProgress = false
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Evaluate")
        }

        //Progress bar displayed while the API call is in progress
        if (isApiCallInProgress) {
            CircularProgressIndicator()
        }
        LazyColumn {
            items(if (selectedOption == Option.SINGLE_EXPRESSION) listOf(result) else results) { result ->
                Text(text = "Expression: ${result?.expression}")
                Text(text = "Result: ${result?.result}")
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

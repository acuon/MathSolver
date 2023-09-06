package com.example.mathsolver.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mathsolver.ui.view.MainViewModel

@Composable
fun DrawerContent(viewModel: MainViewModel, width: Int, scaffoldState: ScaffoldState) {
    val allExpressions by viewModel.expressionsList.collectAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "History",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExpressionHistoryItems(allExpressions, viewModel, scaffoldState)
        }
    }
}

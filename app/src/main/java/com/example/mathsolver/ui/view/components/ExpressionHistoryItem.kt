package com.example.mathsolver.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathsolver.data.database.MathExpressionHistoryEntity
import com.example.mathsolver.utils.getDate
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.mathsolver.ui.view.MainViewModel
import kotlinx.coroutines.launch
@Composable
fun ExpressionHistoryItems(
    allExpressions: List<MathExpressionHistoryEntity>,
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val groupedByDate = allExpressions.groupBy { it.timestamp?.getDate() }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        groupedByDate.keys.forEach { date ->
            if (date != null) {
                item {
                    Text(
                        text = date,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            groupedByDate[date]?.forEach { menuItem ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(onClick = {
                                viewModel.updateExpression(menuItem)
                                coroutineScope.launch {
                                    if (scaffoldState.drawerState.isOpen) scaffoldState.drawerState.close()
                                    else scaffoldState.drawerState.open()
                                }
                            }),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = menuItem.expression ?: "",
                            color = Color.Blue,
                            fontSize = 16.sp,
                        )
                        Text(
                            text = "=${menuItem.result}",
                            color = Color.Green,
                            fontSize = 18.sp,
                        )
                        Divider(color = Color.Gray)
                    }
                }
            }
        }
    }
}

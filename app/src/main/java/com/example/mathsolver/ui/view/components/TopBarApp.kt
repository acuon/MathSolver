package com.example.mathsolver.ui.view.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.mathsolver.R
import com.example.mathsolver.utils.showToast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(context: Context, title: String, onHistoryClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
            navigationIcon = {
                IconButton(onClick = onHistoryClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history), // Use your custom image resource
                        contentDescription = "History"
                    )
                }
            },
        actions = {
        }
    )
}

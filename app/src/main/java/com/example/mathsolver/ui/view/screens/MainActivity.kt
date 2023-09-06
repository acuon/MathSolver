package com.example.mathsolver.ui.view.screens

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathsolver.ui.theme.MathSolverTheme
import com.example.mathsolver.ui.view.MainViewModel
import com.example.mathsolver.ui.view.components.DrawerContent
import com.example.mathsolver.ui.view.components.MathExpressionScreen
import com.example.mathsolver.ui.view.components.TopBarApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            MathSolverTheme {
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val drawerContentWidth = with(LocalDensity.current) {
                    (250.dp.toPx() / density).toInt()
                }

                val insets = LocalWindowInfo.current
                val isKeyboardVisible = insets.isWindowFocused

                var isDrawerOpen by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopBarApp(
                                context = LocalContext.current,
                                title = "Math Solver",
                                onHistoryClicked = {
                                    coroutineScope.launch {
                                        isDrawerOpen = !isDrawerOpen
                                        if(scaffoldState.drawerState.isOpen) scaffoldState.drawerState.close()
                                        else scaffoldState.drawerState.open()
                                    }
                                }
                            )
                        },
                        drawerContent = {
                            DrawerContent(
                                viewModel = viewModel,
                                width = drawerContentWidth,
                                scaffoldState = scaffoldState
                            )
                        },
                        content = {
                            MathExpressionScreen(
                                viewModel = viewModel,
                                context = LocalContext.current,
                                padding = it
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun GreetingPreview() {
    MathSolverTheme {
        Greeting(name = "Android")
    }
}
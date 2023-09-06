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

    // creating viewmodel instance
    // This helps manage data in our app.
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We make sure that the keyboard doesn't change the layout of our app.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        // Now we set up what our app looks like.
        setContent {
            MathSolverTheme {
                // We create a special component to manage our app's layout.
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()

                // We decide how wide the side menu (drawer) should be.
                val drawerContentWidth = with(LocalDensity.current) {
                    (250.dp.toPx() / density).toInt()
                }

                // We check if the keyboard is shown on the screen.
                val insets = LocalWindowInfo.current
                val isKeyboardVisible = insets.isWindowFocused

                // We keep track of whether the side menu (drawer) is open or closed.
                var isDrawerOpen by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            // We create the top part of our app, including the title and history button.
                            TopBarApp(
                                context = LocalContext.current,
                                title = "Math Solver",
                                onHistoryClicked = {
                                    coroutineScope.launch {
                                        // When we click the history button, we toggle the side menu (drawer).
                                        isDrawerOpen = !isDrawerOpen
                                        if(scaffoldState.drawerState.isOpen) scaffoldState.drawerState.close()
                                        else scaffoldState.drawerState.open()
                                    }
                                }
                            )
                        },
                        drawerContent = {
                            // This is where we put content for the side menu (drawer).
                            DrawerContent(
                                viewModel = viewModel,
                                width = drawerContentWidth,
                                scaffoldState = scaffoldState
                            )
                        },
                        content = {
                            // The main content of our app is the MathExpressionScreen.
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

// This is a simple message display component.
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// This is used to preview how the greeting message looks.
@Composable
@Preview(showBackground = true)
fun GreetingPreview() {
    MathSolverTheme {
        Greeting(name = "Android")
    }
}

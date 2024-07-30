package com.example.myapplication.core.util.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.util.presentation.ui.theme.AppTheme
import com.example.myapplication.core.util.presentation.HomeScreenRoute
import com.example.myapplication.core.util.presentation.LoginScreenRoute
import com.example.myapplication.features.home.presentation.HomeScreen
import com.example.myapplication.features.login.presentation.LoginScreen
import com.example.myapplication.features.login.presentation.LoginViewModel
import com.example.myapplication.features.login.presentation.UIEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                println("windowSizeClass: ${windowSizeClass.heightSizeClass}")
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = LoginScreenRoute
                ) {
                    composable<LoginScreenRoute> {
                        val viewModel = hiltViewModel<LoginViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        val uiEvent by viewModel.eventChannel.collectAsStateWithLifecycle(
                            initialValue = null
                        )
                        LoginScreen(
                            windowSizeClass = windowSizeClass,
                            navController = navController,
                            state = state,
                            onAction = viewModel::onAction,
                            event = uiEvent
                        )
                    }
                    composable<HomeScreenRoute> {
                        val viewModel = hiltViewModel<LoginViewModel>()
                        HomeScreen(
                            navController = navController,
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}
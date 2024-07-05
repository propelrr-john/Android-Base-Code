package com.example.myapplication.core.util.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.util.presentation.ui.theme.AppTheme
import com.example.myapplication.core.util.presentation.HomeScreenRoute
import com.example.myapplication.core.util.presentation.LoginScreenRoute
import com.example.myapplication.features.home.presentation.HomeScreen
import com.example.myapplication.features.login.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = LoginScreenRoute
                ) {
                    composable<LoginScreenRoute> {
                        LoginScreen(navController = navController)
                    }
                    composable<HomeScreenRoute> {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}
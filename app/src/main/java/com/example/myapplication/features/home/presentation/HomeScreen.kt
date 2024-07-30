package com.example.myapplication.features.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.core.util.presentation.helpers.ssp
import com.example.myapplication.core.util.presentation.ui.theme.poppinsFontFamily
import com.example.myapplication.features.login.presentation.LoginAction
import com.example.myapplication.features.login.presentation.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    onAction: (LoginAction) -> Unit
) {

    LaunchedEffect(key1 = true) {
        onAction(
            LoginAction.RefreshToken
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home",
            style = TextStyle(
                fontSize = 20.ssp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal
            )
        )
    }
}
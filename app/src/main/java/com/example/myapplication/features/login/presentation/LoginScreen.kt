package com.example.myapplication.features.login.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myapplication.core.util.presentation.HomeScreenRoute
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.core.util.presentation.ui.theme.poppinsFontFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.error) {
        println("Error: ${state.error}")
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventChannel.collectLatest { event ->
            when (event) {
                is UIEvent.LoginSuccess -> {
                    navController.navigate(HomeScreenRoute)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                value = email,
                onValueChange = { text ->
                    email = text
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                value = password,
                onValueChange = { text ->
                    password = text
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = !state.isLoading,
                onClick = {
                    viewModel.onAction(
                        LoginAction.Authenticate(
                            LoginCredential(
                                username = email,
                                password = password
                            )
                        )
                    )
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 4.dp
                        )
                    } else {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = poppinsFontFamily,
            fontSize = 16.sp
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = (1.5).dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = "Email",
                            style = TextStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = poppinsFontFamily,
            fontSize = 16.sp
        ),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = (1.5).dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Password Icon",
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}


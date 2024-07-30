package com.example.myapplication.features.login.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.core.util.presentation.HomeScreenRoute
import com.example.myapplication.core.util.presentation.helpers.sdp
import com.example.myapplication.core.util.presentation.helpers.ssp
import com.example.myapplication.core.util.presentation.ui.theme.avenirFontFamily
import com.example.myapplication.features.login.domain.model.LoginCredential
import com.example.myapplication.core.util.presentation.ui.theme.poppinsFontFamily

@Composable
fun LoginScreen(
    windowSizeClass: WindowSizeClass,
    navController: NavController,
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    event: UIEvent?
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val isPhone by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded
        }
    }

    val isTablet by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded
        }
    }

    LaunchedEffect(key1 = state.error) {
        println("Error: ${state.error}")
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    event?.let {
        LaunchedEffect(key1 = it) {
            when (it) {
                is UIEvent.LoginSuccess -> {
//                    println("AccessToken: ${state.accessToken?.token}")
                    navController.navigate(HomeScreenRoute)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 29.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(
                if (isTablet) {
                    0.12f
                } else {
                    0.19f
                }
            ))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(83.sdp),
                painter = painterResource(id = R.drawable.communitieslogo),
                contentDescription = "App Logo",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.fillMaxHeight(
                if (isTablet) {
                    0.12f
                } else {
                    0.19f
                }
            ))
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isTablet) 35.sdp else 48.sdp),
                value = email,
                onValueChange = { text ->
                    email = text
                }
            )
            Spacer(modifier = Modifier.height(10.sdp))
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isTablet) 35.sdp else 48.sdp),
                value = password,
                onValueChange = { text ->
                    password = text
                }
            )
            Spacer(modifier = Modifier.height(15.sdp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Forgot Password?",
                style = TextStyle(
                    color = Color(0xFF94C7F3),
                    fontSize = 14.ssp,
                    fontFamily = avenirFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End
                )
            )
            Spacer(modifier = Modifier.height(30.sdp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isTablet) 35.sdp else 48.sdp),
                shape = RoundedCornerShape(12.sdp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1267B8),
                    disabledContainerColor = Color(0xFF1267B8)
                ),
                enabled = !state.isLoading,
                onClick = {
                    onAction(
                        LoginAction.Authenticate(
                            LoginCredential(
                                username = email,
                                password = password
                            )
                        )
                    )
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(22.sdp)
                                .padding(2.sdp),
                            color = Color.White,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.sdp
                        )
                    } else {
                        Text(
                            text = "LOGIN",
                            style = TextStyle(
                                fontFamily = avenirFontFamily,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.ssp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
                    .padding(bottom = if (isTablet) {
                        20.sdp
                    } else {
                        40.sdp
                    }),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "New to DMCI Community?",
                    style = TextStyle(
                        color = Color(0xFF94C7F3),
                        fontSize = 16.ssp,
                        fontFamily = avenirFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(9.sdp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isTablet) 35.sdp else 48.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0A447C),
                        disabledContainerColor = Color(0xFF0A447C)
                    ),
                    contentPadding = PaddingValues(horizontal = 18.sdp),
                    onClick = {

                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.sdp)
                                .align(Alignment.CenterStart),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_user),
                            contentDescription = "User icon",
                            tint = Color.White
                        )
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Create a New Account",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.ssp,
                                fontFamily = avenirFontFamily,
                                fontWeight = FontWeight.ExtraBold
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
            color = Color(0xFF466681),
            fontFamily = avenirFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.ssp
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.sdp)
                    )
                    .padding(horizontal = 10.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(35.sdp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.email_icon),
                    contentDescription = "Email Icon",
                    tint = Color(0xFF36CC16)
                )
                Spacer(modifier = Modifier.width(10.sdp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = "Email Address",
                            style = TextStyle(
                                fontFamily = avenirFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.ssp,
                                color = Color(0xFF94C7F3)
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
            color = Color(0xFF466681),
            fontFamily = avenirFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.ssp,
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
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.sdp)
                    )
                    .padding(start = 11.sdp, end = 10.sdp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(35.sdp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.password_icon),
                    contentDescription = "Password Icon",
                    tint = Color(0xFF36CC16)
                )
                Spacer(modifier = Modifier.width(10.sdp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontFamily = avenirFontFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.ssp,
                                color = Color(0xFF94C7F3)
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}


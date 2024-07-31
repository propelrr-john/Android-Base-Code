package com.example.myapplication.core.util.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CustomExtendedFab() {
    val configuration = LocalConfiguration.current
    val lazyListState = rememberLazyListState()
    var isExpanded by remember {
        mutableStateOf(true)
    }
    var isVisible by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(
        targetState = isExpanded,
        label = "transition"
    )
    val animatedWidth by transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            )
        },
        label = "",
        targetValueByState = {
            if (it) {
                configuration.screenWidthDp.dp
            } else {
                70.dp
            }
        }
    )
    val animatedCorners by transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            )
        },
        label = "",
        targetValueByState = {
            if (it) {
                20.dp
            } else {
                50.dp
            }
        }
    )

    LaunchedEffect(key1 = true) {
        isVisible = true
        delay(5000L)
        isExpanded = false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    },
                    animationSpec = tween(800)
                ) + fadeIn()
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .size(
                            height = 70.dp,
                            width = animatedWidth
                        )
                        .clip(RoundedCornerShape(animatedCorners))
                        .background(Color.Gray)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                items = (1..50).toList(),
                key = {
                    it
                }
            ) { item ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
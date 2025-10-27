package com.example.tomatados.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember



@Composable
fun AnimatedEntry(content: @Composable () -> Unit) {
    val (visible, setVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { setVisible(true) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        exit  = fadeOut()
    ) {
        content()
    }
}
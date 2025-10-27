package com.example.tomatados.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    var pressed by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Escala animada (efecto “pulse”)
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        label = "buttonScale"
    )

    Button(
        onClick = {
            coroutineScope.launch {
                pressed = true
                onClick()
                delay(120)
                pressed = false
            }
        },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
    ) {
        Text(text)
    }
}
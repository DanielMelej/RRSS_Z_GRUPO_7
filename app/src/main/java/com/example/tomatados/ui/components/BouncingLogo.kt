package com.example.tomatados.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tomatados.R
import kotlinx.coroutines.delay

@Composable
fun BouncingLogo() {
    var visible by remember { mutableStateOf(false) }
    val scale = remember { Animatable(0f) }

    // 🔹 Movimiento vertical con rebote (sí puede ser negativo)
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else (-120).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoBounce"
    )

    LaunchedEffect(Unit) {
        delay(150)
        visible = true
        try {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )
            )
        } catch (_: Exception) { /* evita crash si se desmonta */ }
    }

    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = "Logo animado de Tomatados",
        modifier = Modifier
            .scale(scale.value)
            .offset(y = offsetY), // ✅ se reemplazó padding() por offset()
        contentScale = ContentScale.Fit
    )
}
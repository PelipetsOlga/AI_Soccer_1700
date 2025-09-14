package com.manager1700.soccer.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.ui.theme.colorWhite

@Composable
fun Modifier.navBarBottomPadding() =
    this.padding(WindowInsets.navigationBars.asPaddingValues())

@Composable
fun Modifier.statusBarTopPadding() =
    this.padding(WindowInsets.statusBars.asPaddingValues())

val cardBrushGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF1C273D),
        Color(0xFF2A3441)
    )
)

val cardBrushLightGradient = Brush.horizontalGradient(
    0.0f to colorWhite.copy(alpha = 0.6f),
    0.25f to colorWhite.copy(alpha = 0f),
    0.51f to colorWhite.copy(alpha = 0.2f),
    1.0f to colorWhite.copy(alpha = 0f),
)

val cardBrushLightLightGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF202B45),
        Color(0xFF0270D5)
    )
)

val cardActiveBrushGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFD361FE),
        Color(0xFF78AAFB)
    )
)

val blueButtonBrushLightGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF0084EA),
        Color(0xFF0368CD)
    )
)

val yellowButtonBrushLightGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFADD53),
        Color(0xFFFEBB08)
    )
)

val cardVeryBigClipShape = RoundedCornerShape(36.dp)
val cardBigClipShape = RoundedCornerShape(16.dp)
val cardClipShape = RoundedCornerShape(8.dp)
val buttonClipShape = RoundedCornerShape(16.dp)

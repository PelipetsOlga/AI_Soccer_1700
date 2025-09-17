package com.manager1700.soccer.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.manager1700.soccer.AppTypography

private val DarkColorScheme = darkColorScheme(
    primary = colorRed,
    secondary = colorGrey_66,
    tertiary = colorGrey_89,
    background = colorBlack,
    surface = colorGrey_3b,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onTertiary = colorWhite,
    onBackground = colorWhite,
    onSurface = colorWhite,
    onSurfaceVariant = colorGrey_89,
    outline = colorGrey_66,
    outlineVariant = colorGrey_2b,
    error = colorRed,
    onError = colorWhite
)

@Composable
fun SoccerManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Force dark color scheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

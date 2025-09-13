package com.manager1700.soccer.ui.feature_splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.components.AutoSizeText
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SplashScreenContract.SideEffect.NavigateToHome -> {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                    viewModel.handleIntent(SplashScreenContract.Intent.NavigationCompleted)
                }
            }
        }
    }

    // Start loading when screen is first composed
    LaunchedEffect(Unit) {
        viewModel.handleIntent(SplashScreenContract.Intent.StartLoading)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularLoadingAnimation()

            AutoSizeText(
                text = "${uiState.progress}%",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(fraction = 0.2f),
            )
        }
    }
}

@PreviewApp
@Composable
fun SplashScreenPreview() {
    SoccerManagerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularLoadingAnimation()

                AutoSizeText(
                    text = "100%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(fraction = 0.2f),
                )
            }
        }
    }
}

@Composable
fun CircularLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(
        modifier = Modifier.size(300.dp) // Increased size from 80dp to 120dp
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.width / 2 * 3 / 4// Adjusted radius for larger size
        val radiusCenter = radius / 2 // Adjusted radius for larger size
        val segmentAngle = 360f / 12

        rotate(rotation, center) {
            for (i in 0 until 12) {
                val angle = i * segmentAngle
                val startAngle = angle - segmentAngle / 2

                val intensity = (i + 1) / 12.toFloat()

                val segmentColor = Color(
                    red = intensity,
                    green = intensity,
                    blue = intensity,
                    alpha = 1f
                )

                // Draw segment
                drawArc(
                    color = segmentColor,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle * 0.75f, // Slightly smaller segments for better spacing
                    useCenter = true,
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    ),
                    size = Size(radius * 2, radius * 2)
                )
                // Draw segment
                drawArc(
                    color = colorBlack,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle, // Slightly smaller segments for better spacing
                    useCenter = true,
                    topLeft = Offset(
                        center.x - radiusCenter,
                        center.y - radiusCenter
                    ),
                    size = Size(radiusCenter * 2, radiusCenter * 2)
                )
            }
        }
    }
}


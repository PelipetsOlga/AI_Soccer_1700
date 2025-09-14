package com.manager1700.soccer.ui.feature_welcome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.welcomeDarkBg
import com.manager1700.soccer.ui.theme.welcomeGreenButton
import com.manager1700.soccer.ui.theme.welcomeRed
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Start animation when screen loads
    LaunchedEffect(Unit) {
        viewModel.setEvent(WelcomeScreenContract.Event.StartAnimation)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is WelcomeScreenContract.Effect.NavigateToHome -> {
                    navController.navigate(Screen.HomeWrapper.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    WelcomeScreenContent(state, onEvent = { viewModel.setEvent(it) })
}

@Composable
fun WelcomeScreenContent(
    state: WelcomeScreenContract.State,
    onEvent: (WelcomeScreenContract.Event) -> Unit,
) {
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold)
    )

    // Animation values
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(1.2f) }
    val contentAlpha = remember { Animatable(0f) }

    // Animate logo appearance
    LaunchedEffect(state.showLogo) {
        if (state.showLogo) {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
            // After logo appears, scale it down
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    // Animate content appearance
    LaunchedEffect(state.showContent) {
        if (state.showContent) {
            contentAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.welcome_background),
            contentDescription = "Welcome background",
            modifier = Modifier.fillMaxSize()
        )
        // Top red curved section
        Image(
            painter = painterResource(id = R.drawable.top_welcome_bar),
            contentDescription = "Top welcome bar",
            modifier = Modifier
                .fillMaxWidth()
                .height(268.dp)
                .align(Alignment.TopCenter)
        )

        // Bottom red curved section
        Image(
            painter = painterResource(id = R.drawable.bottom_welcome_bar),
            contentDescription = "Bottom welcome bar",
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp)
                .align(Alignment.BottomCenter)
        )

        // Main content area with dark background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 100.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Logo with animation
                if (state.showLogo) {
                    Image(
                        painter = painterResource(id = R.mipmap.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .alpha(logoAlpha.value)
                            .scale(logoScale.value)
                    )
                }

                // Content with animation
                if (state.showContent) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .alpha(contentAlpha.value)
                            .padding(top = 24.dp)
                    ) {
                        // Welcome Title
                        Text(
                            text = stringResource(R.string.welcome_title),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFamily,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // App Name
                        Text(
                            text = stringResource(R.string.app_name_full),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFamily,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        // Description
                        Text(
                            text = stringResource(R.string.welcome_text),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFamily,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 48.dp)
                        )

                        // Get Started Button
                        Button(
                            onClick = { onEvent(WelcomeScreenContract.Event.GetStartedClicked) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = welcomeGreenButton
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.btn_get_started),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = montserratFamily,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewApp
@Composable
fun WelcomeScreenContentPreview() {
    SoccerManagerTheme {
        WelcomeScreenContent(
            WelcomeScreenContract.State(
                showLogo = true,
                showContent = true
            ),
            {}
        )
    }
}
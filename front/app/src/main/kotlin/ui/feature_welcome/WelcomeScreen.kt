package com.manager1700.soccer.ui.feature_welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

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

    WelcomeScreenContent(state, onEvent = { viewModel.handleEvent(it) })
}

@Composable
fun WelcomeScreenContent(
    state: WelcomeScreenContract.State,
    onEvent: (WelcomeScreenContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome Title
            Text(
                text = stringResource(R.string.welcome_title).uppercase(),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colorWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Subtitle
            Text(
                text = stringResource(R.string.welcome_text),
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = colorWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Get Started Button
            PrimaryButton(
                onClick = { onEvent(WelcomeScreenContract.Event.GetStartedClicked) },
                text = stringResource(R.string.btn_get_started)
            )
        }
    }
}

@PreviewApp
@Composable
fun WelcomeScreenContentPreview() {
    SoccerManagerTheme {
        WelcomeScreenContent(WelcomeScreenContract.State(), {})
    }
}

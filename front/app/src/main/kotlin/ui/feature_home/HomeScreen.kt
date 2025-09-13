package com.manager1700.soccer.ui.feature_home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeScreenContract.Effect.NavigateToSettings -> {
                    // Handle navigation to settings
                }
            }
        }
    }
    
    HomeScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeScreenContract.State,
    onEvent: (HomeScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Toolbar with settings button
        Toolbar(
            title = stringResource(R.string.home_title),
            showSettingsButton = true,
            onSettingsClick = { onEvent(HomeScreenContract.Event.SettingsClicked) }
        )
        
        // Centered screen title
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.home_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@PreviewApp
@Composable
fun HomeScreenContentPreview() {
    SoccerManagerTheme {
        HomeScreenContent(
            state = HomeScreenContract.State(),
            onEvent = {}
        )
    }
}
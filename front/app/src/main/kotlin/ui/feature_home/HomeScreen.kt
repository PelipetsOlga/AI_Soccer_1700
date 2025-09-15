package com.manager1700.soccer.ui.feature_home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainNavController: NavController,
    bottomNavController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeScreenContract.Effect.NavigateBack -> {
                    // Handle navigation back
                }

                is HomeScreenContract.Effect.NavigateToSettings -> {
                    mainNavController.navigate(Screen.Settings.route)
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
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(R.string.home_title),
                showBackButton = false,
                showSettingsButton = true,
                onBackClick = { },
                onSettingsClick = { onEvent(HomeScreenContract.Event.SettingsClicked) }
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        // Centered screen title
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {
            AppCard(
                title = "Title", modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
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
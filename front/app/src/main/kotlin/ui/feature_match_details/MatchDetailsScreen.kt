package com.manager1700.soccer.ui.feature_match_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    matchId: Int,
    navController: NavController,
    viewModel: MatchDetailsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Load match data when screen is composed
    LaunchedEffect(matchId) {
        viewModel.loadMatchById(matchId)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MatchDetailsScreenContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
                is MatchDetailsScreenContract.Effect.NavigateToEditMatch -> {
                    navController.navigate("edit_match_screen/${effect.matchId}")
                }
                is MatchDetailsScreenContract.Effect.NavigateToMatchAttendance -> {
                    // TODO: Navigate to match attendance
                }
                is MatchDetailsScreenContract.Effect.ShowError -> {
                    // TODO: Show error message
                }
                is MatchDetailsScreenContract.Effect.ShowSuccess -> {
                    // TODO: Show success message
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(R.string.match_details_title),
                showBackButton = true,
                onBackClick = { viewModel.setEvent(MatchDetailsScreenContract.Event.BackClicked) }
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        MatchDetailsScreenContent(
            state = state,
            onEvent = { viewModel.setEvent(it) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@PreviewApp
@Composable
fun MatchDetailsScreenPreview() {
    SoccerManagerTheme {
        // Preview with sample data
    }
}

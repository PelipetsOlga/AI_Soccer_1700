package com.manager1700.soccer.ui.feature_match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.components.MatchFilterTabs
import com.manager1700.soccer.ui.components.MatchCalendar
import com.manager1700.soccer.ui.components.MatchItemCard
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(
    mainNavController: NavController,
    bottomNavController: NavController,
    viewModel: MatchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Reload matches when screen is composed (when user returns from other screens)
    LaunchedEffect(Unit) {
        viewModel.setEvent(MatchScreenContract.Event.ReloadMatches)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MatchScreenContract.Effect.NavigateBack -> {
                    bottomNavController.popBackStack()
                }

                is MatchScreenContract.Effect.NavigateToSettings -> {
                    mainNavController.navigate(Screen.Settings.route)
                }

                is MatchScreenContract.Effect.NavigateToAddMatch -> {
                    mainNavController.navigate(Screen.AddMatch.route)
                }

                is MatchScreenContract.Effect.NavigateToMatchDetails -> {
                    mainNavController.navigate("match_details_screen/${effect.matchId}")
                }

                is MatchScreenContract.Effect.NavigateToMatchAttendance -> {
                    // TODO: Navigate to match attendance
                }

                is MatchScreenContract.Effect.ShowMarkAsDialog -> {
                    // TODO: Show mark as dialog
                }
            }
        }
    }

    MatchScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreenContent(
    state: MatchScreenContract.State,
    onEvent: (MatchScreenContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(R.string.matches_title),
                showBackButton = true,
                showSettingsButton = true,
                onBackClick = { onEvent(MatchScreenContract.Event.BackClicked) },
                onSettingsClick = { onEvent(MatchScreenContract.Event.SettingsClicked) }
            )
        },
        bottomBar = {
            // Fixed Add Match Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorBlack)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                PrimaryButton(
                    onClick = { onEvent(MatchScreenContract.Event.AddMatchClicked) },
                    text = stringResource(R.string.add_match),
                )
            }
        },
        containerColor = colorBlack
    ) { paddingValues ->
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter tabs
            MatchFilterTabs(
                selectedViewType = state.selectedViewType,
                selectedFilterType = state.selectedFilterType,
                onViewTypeChanged = { onEvent(MatchScreenContract.Event.ViewTypeChanged(it)) },
                onFilterTypeChanged = { onEvent(MatchScreenContract.Event.FilterTypeChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Calendar view - show calendar below FilterTabs
                if (state.selectedViewType == MatchScreenContract.ViewType.CALENDAR) {
                    item {
                        MatchCalendar(
                            matches = state.matches,
                            selectedDate = state.selectedDate,
                            onDateSelected = { onEvent(MatchScreenContract.Event.DateSelected(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }


                items(state.matches) { match ->
                    MatchItemCard(
                        match = match,
                        onDetailsClick = {
                            onEvent(
                                MatchScreenContract.Event.MatchDetailsClicked(
                                    match.id
                                )
                            )
                        },
                        onAttendanceClick = {
                            onEvent(
                                MatchScreenContract.Event.MatchAttendanceClicked(
                                    match.id
                                )
                            )
                        },
                        onMarkAsClick = {
                            onEvent(
                                MatchScreenContract.Event.MatchMarkAsClicked(
                                    match.id
                                )
                            )
                        },
                        onStatusChanged = { newStatus ->
                            onEvent(
                                MatchScreenContract.Event.UpdateMatchStatus(
                                    match.id,
                                    newStatus
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@PreviewApp
@Composable
fun MatchScreenContentPreview() {
    SoccerManagerTheme {
        MatchScreenContent(
            state = MatchScreenContract.State(),
            onEvent = {}
        )
    }
}

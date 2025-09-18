package com.manager1700.soccer.ui.feature_training

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
import com.manager1700.soccer.ui.components.FilterTabs
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.components.TrainingCalendar
import com.manager1700.soccer.ui.components.TrainingItemCard
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    mainNavController: NavController,
    bottomNavController: NavController,
    viewModel: TrainingScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Reload trainings when screen is composed (when user returns from other screens)
    LaunchedEffect(Unit) {
        viewModel.setEvent(TrainingScreenContract.Event.ReloadTrainings)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TrainingScreenContract.Effect.NavigateBack -> {
                    bottomNavController.popBackStack()
                }

                is TrainingScreenContract.Effect.NavigateToSettings -> {
                    mainNavController.navigate(Screen.Settings.route)
                }

                is TrainingScreenContract.Effect.NavigateToAddTraining -> {
                    mainNavController.navigate(Screen.AddTraining.route)
                }

                is TrainingScreenContract.Effect.NavigateToTrainingDetails -> {
                    mainNavController.navigate("training_details_screen/${effect.trainingId}")
                }

                is TrainingScreenContract.Effect.NavigateToTrainingAttendance -> {
                    // TODO: Navigate to training attendance
                }

                is TrainingScreenContract.Effect.ShowMarkAsDialog -> {
                    // TODO: Show mark as dialog
                }
            }
        }
    }

    TrainingScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreenContent(
    state: TrainingScreenContract.State,
    onEvent: (TrainingScreenContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(R.string.training_title),
                showBackButton = true,
                showSettingsButton = true,
                onBackClick = { onEvent(TrainingScreenContract.Event.BackClicked) },
                onSettingsClick = { onEvent(TrainingScreenContract.Event.SettingsClicked) }
            )
        },
        bottomBar = {
            // Fixed Add Training Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorBlack)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                PrimaryButton(
                    onClick = { onEvent(TrainingScreenContract.Event.AddTrainingClicked) },
                    text = stringResource(R.string.add_training),
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
            FilterTabs(
                selectedViewType = state.selectedViewType,
                selectedFilterType = state.selectedFilterType,
                onViewTypeChanged = { onEvent(TrainingScreenContract.Event.ViewTypeChanged(it)) },
                onFilterTypeChanged = { onEvent(TrainingScreenContract.Event.FilterTypeChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Calendar view - show calendar below FilterTabs
                if (state.selectedViewType == TrainingScreenContract.ViewType.CALENDAR) {
                    item {
                        TrainingCalendar(
                            trainings = state.trainings,
                            selectedDate = state.selectedDate,
                            onDateSelected = { onEvent(TrainingScreenContract.Event.DateSelected(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }


                items(state.trainings) { training ->
                    TrainingItemCard(
                        training = training,
                        onDetailsClick = {
                            onEvent(
                                TrainingScreenContract.Event.TrainingDetailsClicked(
                                    training.id
                                )
                            )
                        },
                        onAttendanceClick = {
                            onEvent(
                                TrainingScreenContract.Event.TrainingAttendanceClicked(
                                    training.id
                                )
                            )
                        },
                        onMarkAsClick = {
                            onEvent(
                                TrainingScreenContract.Event.TrainingMarkAsClicked(
                                    training.id
                                )
                            )
                        },
                        onStatusChanged = { newStatus ->
                            onEvent(
                                TrainingScreenContract.Event.UpdateTrainingStatus(
                                    training.id,
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
fun TrainingScreenContentPreview() {
    SoccerManagerTheme {
        TrainingScreenContent(
            state = TrainingScreenContract.State(),
            onEvent = {}
        )
    }
}

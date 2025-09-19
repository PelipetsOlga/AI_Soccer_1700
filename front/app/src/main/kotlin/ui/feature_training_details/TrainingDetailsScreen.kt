package com.manager1700.soccer.ui.feature_training_details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.colorBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailsScreen(
    trainingId: Int,
    navController: NavController,
    viewModel: TrainingDetailsScreenViewModel = hiltViewModel()
) {
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TrainingDetailsScreenContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
                is TrainingDetailsScreenContract.Effect.NavigateToEditTraining -> {
                    navController.navigate("edit_training_screen/${effect.trainingId}")
                }
                is TrainingDetailsScreenContract.Effect.NavigateToAttendance -> {
                    // TODO: Navigate to attendance
                }
                is TrainingDetailsScreenContract.Effect.ShowMarkAsDialog -> {
                    // TODO: Show mark as dialog
                }
                is TrainingDetailsScreenContract.Effect.ShowError -> {
                    // TODO: Show error message
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = "Details",
                showBackButton = true,
                showSettingsButton = false,
                onBackClick = { viewModel.setEvent(TrainingDetailsScreenContract.Event.BackClicked) },
                onSettingsClick = { }
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        TrainingDetailsScreenContent(
            trainingId = trainingId,
            onEvent = { viewModel.setEvent(it) },
            viewModel = viewModel,
            paddingValues = paddingValues,
        )
    }
}

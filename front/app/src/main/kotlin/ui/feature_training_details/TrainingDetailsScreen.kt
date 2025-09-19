package com.manager1700.soccer.ui.feature_training_details

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.components.AddExerciseAlert
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailsScreen(
    trainingId: Int,
    navController: NavController,
    viewModel: TrainingDetailsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    
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

    // Add/Edit Exercise Dialog
    if (state.showAddExerciseDialog) {
        AddExerciseAlert(
            exerciseType = state.exerciseType,
            exerciseDuration = state.exerciseDuration,
            onExerciseTypeChanged = { viewModel.setEvent(TrainingDetailsScreenContract.Event.ExerciseTypeChanged(it)) },
            onExerciseDurationChanged = { viewModel.setEvent(TrainingDetailsScreenContract.Event.ExerciseDurationChanged(it)) },
            onConfirm = { viewModel.setEvent(TrainingDetailsScreenContract.Event.ConfirmAddExercise) },
            onCancel = { viewModel.setEvent(TrainingDetailsScreenContract.Event.CancelAddExercise) },
            isEditMode = state.isEditMode,
            onRemove = if (state.isEditMode) { { viewModel.setEvent(TrainingDetailsScreenContract.Event.RemoveExercise) } } else null
        )
    }

    // Clear Exercises Confirmation Dialog
    if (state.showClearExercisesDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setEvent(TrainingDetailsScreenContract.Event.CancelClearExercises) },
            title = {
                Text(
                    text = stringResource(R.string.clear_exercises_confirm_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = Montserrat
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.setEvent(TrainingDetailsScreenContract.Event.ConfirmClearExercises) }
                ) {
                    Text(
                        text = stringResource(R.string.clear_exercises_yes),
                        color = colorWhite,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.setEvent(TrainingDetailsScreenContract.Event.CancelClearExercises) }
                ) {
                    Text(
                        text = stringResource(R.string.clear_exercises_no),
                        color = colorWhite,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

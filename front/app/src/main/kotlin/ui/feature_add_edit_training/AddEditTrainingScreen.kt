package com.manager1700.soccer.ui.feature_add_edit_training

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.statusBarTopPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTrainingScreen(
    training: Training?,
    navController: NavController,
    viewModel: AddEditTrainingViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Initialize with training data
    LaunchedEffect(training) {
        viewModel.initializeWithTraining(training, training != null)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddEditTrainingContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
                is AddEditTrainingContract.Effect.NavigateToTraining -> {
                    navController.popBackStack()
                }
                is AddEditTrainingContract.Effect.ShowError -> {
                    // Handle error - could show a snackbar or dialog
                    // For now, just log it
                    android.util.Log.e("AddEditTraining", effect.message)
                }
            }
        }
    }

    AddEditTrainingScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) },
        isEditMode = training != null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTrainingScreenContent(
    state: AddEditTrainingContract.State,
    onEvent: (AddEditTrainingContract.Event) -> Unit,
    isEditMode: Boolean
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = if (isEditMode) stringResource(R.string.edit_training_title) else stringResource(R.string.add_training_title),
                showBackButton = true,
                showSettingsButton = false,
                onBackClick = { onEvent(AddEditTrainingContract.Event.BackClicked) },
                onSettingsClick = { }
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        AddEditTrainingContent(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
    }
}

@PreviewApp
@Composable
fun AddEditTrainingScreenPreview() {
    SoccerManagerTheme {
        AddEditTrainingScreenContent(
            state = AddEditTrainingContract.State(
                isEditMode = false,
                type = "",
                date = "",
                startTime = "",
                endTime = "",
                venue = "",
                note = ""
            ),
            onEvent = {},
            isEditMode = false
        )
    }
}

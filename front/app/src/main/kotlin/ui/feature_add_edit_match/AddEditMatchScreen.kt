package com.manager1700.soccer.ui.feature_add_edit_match

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.statusBarTopPadding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMatchScreen(
    match: Match? = null,
    navController: NavController,
    matchId: Int? = null,
    viewModel: AddEditMatchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Date picker dialog
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                viewModel.setEvent(AddEditMatchContract.Event.DateChanged(selectedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))))
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
    }

    // Start time picker dialog
    val startTimePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                viewModel.setEvent(AddEditMatchContract.Event.StartTimeChanged(selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))))
            },
            LocalTime.now().hour,
            LocalTime.now().minute,
            true
        )
    }

    // End time picker dialog
    val endTimePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                viewModel.setEvent(AddEditMatchContract.Event.EndTimeChanged(selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))))
            },
            LocalTime.now().plusHours(1).hour,
            LocalTime.now().plusHours(1).minute,
            true
        )
    }

    // Initialize with match data
    LaunchedEffect(match, matchId) {
        if (matchId != null && match == null) {
            viewModel.loadMatchById(matchId)
        } else {
            viewModel.initializeWithMatch(match, match != null)
        }
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddEditMatchContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
                is AddEditMatchContract.Effect.NavigateToMatchDetails -> {
                    // TODO: Navigate to match details
                }
                is AddEditMatchContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }
                is AddEditMatchContract.Effect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = if (state.isEditMode) stringResource(R.string.edit_match_title) else stringResource(R.string.add_match_title),
                showBackButton = true,
                onBackClick = { viewModel.setEvent(AddEditMatchContract.Event.BackClicked) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = colorBlack
    ) { paddingValues ->
        AddEditMatchContent(
            state = state,
            onEvent = { event ->
                when (event) {
                    AddEditMatchContract.Event.DatePickerClicked -> datePickerDialog.show()
                    AddEditMatchContract.Event.StartTimePickerClicked -> startTimePickerDialog.show()
                    AddEditMatchContract.Event.EndTimePickerClicked -> endTimePickerDialog.show()
                    else -> viewModel.setEvent(event)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .statusBarTopPadding()
        )
    }
}

@PreviewApp
@Composable
fun AddEditMatchScreenPreview() {
    SoccerManagerTheme {
        // Preview with sample data
    }
}

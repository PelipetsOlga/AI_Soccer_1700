package com.manager1700.soccer.ui.feature_add_edit_training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
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
import com.manager1700.soccer.ui.components.input.formatDate
import com.manager1700.soccer.ui.components.input.formatTime
import com.manager1700.soccer.ui.components.input.TrainingTypes
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTrainingScreen(
    training: Training?,
    navController: NavController,
    trainingId: Int? = null,
    viewModel: AddEditTrainingViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showTypePicker by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Initialize with training data
    LaunchedEffect(training, trainingId) {
        if (trainingId != null && training == null) {
            // Load training by ID for edit mode
            viewModel.loadTrainingById(trainingId)
        } else {
            // Use provided training data
            viewModel.initializeWithTraining(training, training != null)
        }
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
                    // Show error toast
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Long
                    )
                }
                is AddEditTrainingContract.Effect.ShowDatePicker -> {
                    showDatePicker = true
                }
                is AddEditTrainingContract.Effect.ShowStartTimePicker -> {
                    showStartTimePicker = true
                }
                is AddEditTrainingContract.Effect.ShowEndTimePicker -> {
                    showEndTimePicker = true
                }
                is AddEditTrainingContract.Effect.ShowTypePicker -> {
                    showTypePicker = true
                }
            }
        }
    }

    AddEditTrainingScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) },
        isEditMode = training != null,
        snackbarHostState = snackbarHostState
    )

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Show Date Picker
    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            val datePickerDialog = DatePickerDialog(
                context,
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    viewModel.setEvent(AddEditTrainingContract.Event.DateChanged(formatDate(selectedDate)))
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    // Show Start Time Picker
    LaunchedEffect(showStartTimePicker) {
        if (showStartTimePicker) {
            val timePickerDialog = TimePickerDialog(
                context,
                R.style.CustomTimePickerDialog,
                { _, hourOfDay, minute ->
                    val selectedTime = LocalTime.of(hourOfDay, minute)
                    viewModel.setEvent(AddEditTrainingContract.Event.StartTimeChanged(formatTime(selectedTime)))
                    showStartTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }

    // Show End Time Picker
    LaunchedEffect(showEndTimePicker) {
        if (showEndTimePicker) {
            val timePickerDialog = TimePickerDialog(
                context,
                R.style.CustomTimePickerDialog,
                { _, hourOfDay, minute ->
                    val selectedTime = LocalTime.of(hourOfDay, minute)
                    viewModel.setEvent(AddEditTrainingContract.Event.EndTimeChanged(formatTime(selectedTime)))
                    showEndTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }

    // Show Type Picker
    LaunchedEffect(showTypePicker) {
        if (showTypePicker) {
            // This will be handled by the AlertDialog below
        }
    }

    // Type Picker Dialog
    if (showTypePicker) {
        AlertDialog(
            onDismissRequest = { showTypePicker = false },
            title = { androidx.compose.material3.Text("Select Training Type") },
            text = {
                Column {
                    TrainingTypes.ALL_TYPES.forEach { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setEvent(AddEditTrainingContract.Event.TypeChanged(type))
                                    showTypePicker = false
                                }
                                .padding(16.dp)
                        ) {
                            androidx.compose.material3.Text(
                                text = type,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showTypePicker = false }
                ) {
                    androidx.compose.material3.Text("Cancel")
                }
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTrainingScreenContent(
    state: AddEditTrainingContract.State,
    onEvent: (AddEditTrainingContract.Event) -> Unit,
    isEditMode: Boolean,
    snackbarHostState: SnackbarHostState
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            isEditMode = false,
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}

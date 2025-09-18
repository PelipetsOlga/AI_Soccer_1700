package com.manager1700.soccer.ui.feature_add_edit_training

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.models.AttendanceInfo
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.domain.models.Training
import java.time.LocalDate
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import com.manager1700.soccer.ui.feature_add_edit_training.AddEditTrainingContract.isCreateTrainingFormValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditTrainingViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
        AddEditTrainingContract.Event,
        AddEditTrainingContract.State,
        AddEditTrainingContract.Effect
        >() {

    override fun createInitialState(): AddEditTrainingContract.State {
        return AddEditTrainingContract.State()
    }

    fun initializeWithTraining(training: Training?, isEditMode: Boolean) {
        setState {
            copy(
                training = training,
                isEditMode = isEditMode,
                type = training?.type.orEmpty(),
                date = "", // Training model doesn't have date field, using empty for now
                startTime = training?.startDateTime?.toString().orEmpty(),
                endTime = training?.endDateTime?.toString().orEmpty(),
                venue = training?.place.orEmpty(),
                note = training?.note.orEmpty()
            )
        }
    }

    override fun handleEvent(event: AddEditTrainingContract.Event) {
        when (event) {
            is AddEditTrainingContract.Event.BackClicked -> handleBackClicked()
            is AddEditTrainingContract.Event.SaveClicked -> handleSaveClicked()
            is AddEditTrainingContract.Event.CancelClicked -> handleCancelClicked()
            is AddEditTrainingContract.Event.TypeChanged -> handleTypeChanged(event.type)
            is AddEditTrainingContract.Event.DateChanged -> handleDateChanged(event.date)
            is AddEditTrainingContract.Event.StartTimeChanged -> handleStartTimeChanged(event.startTime)
            is AddEditTrainingContract.Event.EndTimeChanged -> handleEndTimeChanged(event.endTime)
            is AddEditTrainingContract.Event.VenueChanged -> handleVenueChanged(event.venue)
            is AddEditTrainingContract.Event.NoteChanged -> handleNoteChanged(event.note)
            is AddEditTrainingContract.Event.DatePickerClicked -> handleDatePickerClicked()
            is AddEditTrainingContract.Event.StartTimePickerClicked -> handleStartTimePickerClicked()
            is AddEditTrainingContract.Event.EndTimePickerClicked -> handleEndTimePickerClicked()
            is AddEditTrainingContract.Event.TypePickerClicked -> handleTypePickerClicked()
        }
    }

    private fun handleBackClicked() {
        setEffect { AddEditTrainingContract.Effect.NavigateBack }
    }

    private fun handleCancelClicked() {
        setEffect { AddEditTrainingContract.Effect.NavigateBack }
    }

    private fun handleSaveClicked() {
        val currentState = viewState.value
        Log.d("AddEditTraining", "handleSaveClicked")

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val training = createTrainingFromState(currentState)
                Log.d("AddEditTraining", "createTrainingFromState $training")

                if (currentState.isEditMode) {
                    Log.d("AddEditTraining", "update")
                    repository.updateTraining(training)
                } else {
                    Log.d("AddEditTraining", "insert")
                    repository.createTraining(training)
                    Log.d("AddEditTraining", "Created training successfully")
                }
                setState { copy(isLoading = false) }
                setEffect { AddEditTrainingContract.Effect.NavigateToTraining }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { AddEditTrainingContract.Effect.ShowError("Failed to save training: ${e.message}") }
            }
        }
    }

    private fun createTrainingFromState(state: AddEditTrainingContract.State): Training {
        val trainingId = if (state.isEditMode) state.training?.id ?: 0 else 0
        
        // Parse date from string (format: "dd MM yyyy")
        val date = try {
            val parts = state.date.split(" ")
            if (parts.size == 3) {
                LocalDate.of(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
            } else {
                LocalDate.now() // fallback to today
            }
        } catch (e: Exception) {
            LocalDate.now() // fallback to today
        }

        return Training(
            id = trainingId,
            status = SportEventStatus.Scheduled,
            date = date,
            startDateTime = LocalTime.parse(state.startTime),
            endDateTime = LocalTime.parse(state.endTime),
            type = state.type,
            note = state.note,
            place = state.venue,
            title = state.type, // Using type as title for now
            photos = emptyList(),
            exercises = emptyList(),
            plannedAttendance = AttendanceInfo(emptyMap()),
            realAttendance = AttendanceInfo(emptyMap())
        )
    }

    private fun handleTypeChanged(type: String) {
        setState { 
            copy(type = type, isFormValid = isCreateTrainingFormValid(copy(type = type)))
        }
    }

    private fun handleDateChanged(date: String) {
        setState { 
            copy(date = date, isFormValid = isCreateTrainingFormValid(copy(date = date)))
        }
    }

    private fun handleStartTimeChanged(startTime: String) {
        setState { 
            copy(startTime = startTime, isFormValid = isCreateTrainingFormValid(copy(startTime = startTime)))
        }
    }

    private fun handleEndTimeChanged(endTime: String) {
        setState { 
            copy(endTime = endTime, isFormValid = isCreateTrainingFormValid(copy(endTime = endTime)))
        }
    }

    private fun handleVenueChanged(venue: String) {
        setState { 
            copy(venue = venue, isFormValid = isCreateTrainingFormValid(copy(venue = venue)))
        }
    }

    private fun handleNoteChanged(note: String) {
        setState { 
            copy(note = note, isFormValid = isCreateTrainingFormValid(copy(note = note)))
        }
    }

    private fun handleDatePickerClicked() {
        setEffect { AddEditTrainingContract.Effect.ShowDatePicker }
    }

    private fun handleStartTimePickerClicked() {
        setEffect { AddEditTrainingContract.Effect.ShowStartTimePicker }
    }

    private fun handleEndTimePickerClicked() {
        setEffect { AddEditTrainingContract.Effect.ShowEndTimePicker }
    }

    private fun handleTypePickerClicked() {
        setEffect { AddEditTrainingContract.Effect.ShowTypePicker }
    }
}

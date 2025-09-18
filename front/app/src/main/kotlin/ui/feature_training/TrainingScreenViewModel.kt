package com.manager1700.soccer.ui.feature_training

import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
        TrainingScreenContract.Event,
        TrainingScreenContract.State,
        TrainingScreenContract.Effect
        >() {

    override fun createInitialState(): TrainingScreenContract.State {
        return TrainingScreenContract.State()
    }

    init {
        loadTrainings()
    }

    fun refreshTrainings() {
        loadTrainings()
    }

    override fun handleEvent(event: TrainingScreenContract.Event) {
        when (event) {
            is TrainingScreenContract.Event.BackClicked -> handleBackClicked()
            is TrainingScreenContract.Event.SettingsClicked -> handleSettingsClicked()
            is TrainingScreenContract.Event.AddTrainingClicked -> handleAddTrainingClicked()
            is TrainingScreenContract.Event.ViewTypeChanged -> handleViewTypeChanged(event.viewType)
            is TrainingScreenContract.Event.FilterTypeChanged -> handleFilterTypeChanged(event.filterType)
            is TrainingScreenContract.Event.TrainingDetailsClicked -> handleTrainingDetailsClicked(
                event.trainingId
            )

            is TrainingScreenContract.Event.TrainingAttendanceClicked -> handleTrainingAttendanceClicked(
                event.trainingId
            )

            is TrainingScreenContract.Event.TrainingMarkAsClicked -> handleTrainingMarkAsClicked(
                event.trainingId
            )

            is TrainingScreenContract.Event.DateSelected -> handleDateSelected(event.date)
            is TrainingScreenContract.Event.ReloadTrainings -> loadTrainings()
            is TrainingScreenContract.Event.UpdateTrainingStatus -> handleUpdateTrainingStatus(
                event.trainingId,
                event.newStatus
            )
        }
    }

    private fun handleBackClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateBack }
    }

    private fun handleSettingsClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateToSettings }
    }

    private fun handleAddTrainingClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateToAddTraining }
    }

    private fun handleViewTypeChanged(viewType: TrainingScreenContract.ViewType) {
        setState { copy(selectedViewType = viewType) }
        loadTrainings()
    }

    private fun handleFilterTypeChanged(filterType: TrainingScreenContract.FilterType) {
        setState { copy(selectedFilterType = filterType) }
        loadTrainings()
    }

    private fun handleTrainingDetailsClicked(trainingId: Int) {
        setEffect { TrainingScreenContract.Effect.NavigateToTrainingDetails(trainingId) }
    }

    private fun handleTrainingAttendanceClicked(trainingId: Int) {
        setEffect { TrainingScreenContract.Effect.NavigateToTrainingAttendance(trainingId) }
    }

    private fun handleTrainingMarkAsClicked(trainingId: Int) {
        setEffect { TrainingScreenContract.Effect.ShowMarkAsDialog(trainingId) }
    }

    private fun loadTrainings() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                val allTrainings = repository.getAllTrainings()
                val state = viewState.value
                val filteredTrainings = filterTrainings(
                    allTrainings, 
                    state.selectedFilterType,
                    state.selectedViewType,
                    state.selectedDate
                )
                setState {
                    copy(
                        trainings = filteredTrainings,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                // Handle error
            }
        }
    }

    private fun handleDateSelected(date: LocalDate) {
        setState {
            copy(
                selectedDate = date
            )
        }
        // Reload trainings to show filtered results
        loadTrainings()
    }

    private fun handleUpdateTrainingStatus(
        trainingId: Int,
        newStatus: com.manager1700.soccer.domain.models.SportEventStatus
    ) {
        viewModelScope.launch {
            try {
                // Get the current training
                val currentTraining = repository.getTrainingById(trainingId)

                // Create updated training with new status
                val updatedTraining = currentTraining.copy(status = newStatus)

                // Update in database
                repository.updateTraining(updatedTraining)

                // Reload trainings to update UI
                loadTrainings()
            } catch (e: Exception) {
                // Handle error - could show a snackbar or toast
                // For now, just log the error
                android.util.Log.e("TrainingScreenViewModel", "Error updating training status", e)
            }
        }
    }

    private fun filterTrainings(
        trainings: List<Training>,
        filterType: TrainingScreenContract.FilterType,
        viewType: TrainingScreenContract.ViewType,
        selectedDate: LocalDate? = null,
    ): List<Training> {
        val today = LocalDate.now()

        return if (viewType == TrainingScreenContract.ViewType.LIST) {
            // LIST view logic
            when (filterType) {
                TrainingScreenContract.FilterType.ALL -> trainings
                TrainingScreenContract.FilterType.PAST -> trainings.filter { it.date.isBefore(today) }
                TrainingScreenContract.FilterType.UPCOMING -> trainings.filter { 
                    it.date.isAfter(today) || it.date.isEqual(today) 
                }
            }
        } else if (viewType == TrainingScreenContract.ViewType.CALENDAR) {
            // CALENDAR view logic
            if (selectedDate == null) {
                emptyList()
            } else {
                trainings.filter { it.date == selectedDate }
            }
        } else {
            trainings
        }
    }
}

package com.manager1700.soccer.ui.feature_add_edit_training

import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState
import java.time.LocalTime

/**
 * MVI Contract for AddEditTrainingScreen
 */
object AddEditTrainingContract {

    /**
     * UI State for the add/edit training screen
     */
    data class State(
        val isLoading: Boolean = false,
        val training: Training? = null,
        val isEditMode: Boolean = false,
        val type: String = "",
        val date: String = "",
        val startTime: String = "",
        val endTime: String = "",
        val venue: String = "",
        val note: String = "",
        val errorMessage: String? = null,
        val isFormValid: Boolean = false,
    ) : UiState

    fun isCreateTrainingFormValid(state: State): Boolean {
        if (state.training != null) return true
        return state.type.isNotEmpty()
                && state.date.isNotEmpty()
                && state.startTime.isNotEmpty()
                && state.endTime.isNotEmpty()
                && state.venue.isNotEmpty()
    }

    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SaveClicked : Event()
        data object CancelClicked : Event()
        data class TypeChanged(val type: String) : Event()
        data class DateChanged(val date: String) : Event()
        data class StartTimeChanged(val startTime: String) : Event()
        data class EndTimeChanged(val endTime: String) : Event()
        data class VenueChanged(val venue: String) : Event()
        data class NoteChanged(val note: String) : Event()
    }

    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToTraining : Effect()
        data class ShowError(val message: String) : Effect()
    }
}

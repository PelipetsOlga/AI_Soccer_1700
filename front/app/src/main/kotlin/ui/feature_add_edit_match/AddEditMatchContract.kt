package com.manager1700.soccer.ui.feature_add_edit_match

import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for AddEditMatchScreen
 */
object AddEditMatchContract {
    
    /**
     * UI State for the add/edit match screen
     */
    data class State(
        val isLoading: Boolean = false,
        val match: Match? = null,
        val isEditMode: Boolean = false,
        val type: String = "",
        val opponent: String = "",
        val date: String = "",
        val startTime: String = "",
        val endTime: String = "",
        val venue: String = "",
        val note: String = "",
        val errorMessage: String? = null
    ) : UiState {
        val isFormValid: Boolean
            get() = type.isNotBlank() &&
                    opponent.isNotBlank() &&
                    date.isNotBlank() &&
                    startTime.isNotBlank() &&
                    endTime.isNotBlank() &&
                    venue.isNotBlank()
    }
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SaveClicked : Event()
        data class TypeChanged(val type: String) : Event()
        data class OpponentChanged(val opponent: String) : Event()
        data class DateChanged(val date: String) : Event()
        data class StartTimeChanged(val time: String) : Event()
        data class EndTimeChanged(val time: String) : Event()
        data class VenueChanged(val venue: String) : Event()
        data class NoteChanged(val note: String) : Event()
        data object DatePickerClicked : Event()
        data object StartTimePickerClicked : Event()
        data object EndTimePickerClicked : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToMatchDetails : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}

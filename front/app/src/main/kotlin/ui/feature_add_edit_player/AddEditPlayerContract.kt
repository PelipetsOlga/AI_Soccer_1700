package com.manager1700.soccer.ui.feature_add_edit_player

import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for AddEditPlayerScreen
 */
object AddEditPlayerContract {
    
    /**
     * UI State for the add/edit player screen
     */
    data class State(
        val isLoading: Boolean = false,
        val player: Player? = null,
        val isEditMode: Boolean = false,
        val playerNumber: String = "",
        val position: String = "",
        val foot: String = "",
        val attendance: String = "",
        val sessions: String = "",
        val fitness: String = "",
        val note: String = "",
        val errorMessage: String? = null
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SaveClicked : Event()
        data object CancelClicked : Event()
        data class PlayerNumberChanged(val number: String) : Event()
        data class PositionChanged(val position: String) : Event()
        data class FootChanged(val foot: String) : Event()
        data class AttendanceChanged(val attendance: String) : Event()
        data class SessionsChanged(val sessions: String) : Event()
        data class FitnessChanged(val fitness: String) : Event()
        data class NoteChanged(val note: String) : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToTeam : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}

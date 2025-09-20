package com.manager1700.soccer.ui.feature_match_details

import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for MatchDetailsScreen
 */
object MatchDetailsScreenContract {
    
    /**
     * UI State for the match details screen
     */
    data class State(
        val isLoading: Boolean = false,
        val match: Match? = null,
        val errorMessage: String? = null
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object EditClicked : Event()
        data object AttendanceClicked : Event()
        data object MarkAsClicked : Event()
        data class StatusChanged(val status: com.manager1700.soccer.domain.models.SportEventStatus) : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class NavigateToEditMatch(val matchId: Int) : Effect()
        data class NavigateToMatchAttendance(val matchId: Int) : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}

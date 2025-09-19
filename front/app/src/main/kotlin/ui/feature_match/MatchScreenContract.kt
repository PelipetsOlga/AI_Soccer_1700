package com.manager1700.soccer.ui.feature_match

import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState
import java.time.LocalDate

/**
 * MVI Contract for MatchScreen
 */
object MatchScreenContract {
    
    enum class ViewType {
        LIST, CALENDAR
    }
    
    enum class FilterType {
        ALL, UPCOMING, PAST
    }
    
    /**
     * UI State for the match screen
     */
    data class State(
        val isLoading: Boolean = false,
        val matches: List<Match> = emptyList(),
        val selectedViewType: ViewType = ViewType.LIST,
        val selectedFilterType: FilterType = FilterType.ALL,
        val selectedDate: LocalDate? = null,
        val showCalendar: Boolean = false
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SettingsClicked : Event()
        data object AddMatchClicked : Event()
        data class ViewTypeChanged(val viewType: ViewType) : Event()
        data class FilterTypeChanged(val filterType: FilterType) : Event()
        data class MatchDetailsClicked(val matchId: Int) : Event()
        data class MatchAttendanceClicked(val matchId: Int) : Event()
        data class MatchMarkAsClicked(val matchId: Int) : Event()
        data class DateSelected(val date: LocalDate) : Event()
        data object ReloadMatches : Event()
        data class UpdateMatchStatus(val matchId: Int, val newStatus: com.manager1700.soccer.domain.models.SportEventStatus) : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToSettings : Effect()
        data object NavigateToAddMatch : Effect()
        data class NavigateToMatchDetails(val matchId: Int) : Effect()
        data class NavigateToMatchAttendance(val matchId: Int) : Effect()
        data class ShowMarkAsDialog(val matchId: Int) : Effect()
    }
}

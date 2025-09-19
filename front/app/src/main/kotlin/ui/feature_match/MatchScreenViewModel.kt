package com.manager1700.soccer.ui.feature_match

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MatchScreenViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
        MatchScreenContract.Event,
        MatchScreenContract.State,
        MatchScreenContract.Effect
        >() {

    override fun createInitialState(): MatchScreenContract.State {
        return MatchScreenContract.State()
    }

    override fun handleEvent(event: MatchScreenContract.Event) {
        when (event) {
            is MatchScreenContract.Event.BackClicked -> handleBackClicked()
            is MatchScreenContract.Event.SettingsClicked -> handleSettingsClicked()
            is MatchScreenContract.Event.AddMatchClicked -> handleAddMatchClicked()
            is MatchScreenContract.Event.ViewTypeChanged -> handleViewTypeChanged(event.viewType)
            is MatchScreenContract.Event.FilterTypeChanged -> handleFilterTypeChanged(event.filterType)
            is MatchScreenContract.Event.MatchDetailsClicked -> handleMatchDetailsClicked(event.matchId)
            is MatchScreenContract.Event.MatchAttendanceClicked -> handleMatchAttendanceClicked(
                event.matchId
            )

            is MatchScreenContract.Event.MatchMarkAsClicked -> handleMatchMarkAsClicked(event.matchId)
            is MatchScreenContract.Event.DateSelected -> handleDateSelected(event.date)
            is MatchScreenContract.Event.ReloadMatches -> handleReloadMatches()
            is MatchScreenContract.Event.UpdateMatchStatus -> handleUpdateMatchStatus(
                event.matchId,
                event.newStatus
            )
        }
    }

    private fun handleBackClicked() {
        setEffect { MatchScreenContract.Effect.NavigateBack }
    }

    private fun handleSettingsClicked() {
        setEffect { MatchScreenContract.Effect.NavigateToSettings }
    }

    private fun handleAddMatchClicked() {
        setEffect { MatchScreenContract.Effect.NavigateToAddMatch }
    }

    private fun handleViewTypeChanged(viewType: MatchScreenContract.ViewType) {
        setState { copy(selectedViewType = viewType) }
        loadMatches()
    }

    private fun handleFilterTypeChanged(filterType: MatchScreenContract.FilterType) {
        setState { copy(selectedFilterType = filterType) }
        loadMatches()
    }

    private fun handleMatchDetailsClicked(matchId: Int) {
        setEffect { MatchScreenContract.Effect.NavigateToMatchDetails(matchId) }
    }

    private fun handleMatchAttendanceClicked(matchId: Int) {
        setEffect { MatchScreenContract.Effect.NavigateToMatchAttendance(matchId) }
    }

    private fun handleMatchMarkAsClicked(matchId: Int) {
        setEffect { MatchScreenContract.Effect.ShowMarkAsDialog(matchId) }
    }

    private fun handleDateSelected(date: LocalDate) {
        setState { copy(selectedDate = date) }
        loadMatches()
    }

    private fun handleReloadMatches() {
        loadMatches()
    }

    private fun handleUpdateMatchStatus(
        matchId: Int,
        newStatus: com.manager1700.soccer.domain.models.SportEventStatus
    ) {
        viewModelScope.launch {
            try {
                val match = repository.getMatchById(matchId)
                val updatedMatch = match.copy(status = newStatus)
                repository.updateMatch(updatedMatch)
                loadMatches() // Reload to reflect changes
            } catch (e: Exception) {
                Log.e("MatchScreenViewModel", "Error updating match status", e)
            }
        }
    }

    private fun loadMatches() {
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }

                val matchesInList = when (viewState.value.selectedFilterType) {
                    MatchScreenContract.FilterType.ALL -> repository.getAllMatches()
                    MatchScreenContract.FilterType.UPCOMING -> repository.getFutureMatchs()
                    MatchScreenContract.FilterType.PAST -> repository.getPastMatchs()
                }

                val matchesForDay = if (viewState.value.selectedDate != null) {
                    repository.getMatchsForDay(viewState.value.selectedDate!!)
                } else {
                    matchesInList
                }

                setState {
                    copy(
                        matches = if (viewState.value.selectedViewType == MatchScreenContract.ViewType.CALENDAR) {
                            matchesForDay
                        } else {
                            matchesInList
                        },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("MatchScreenViewModel", "Error loading matches", e)
                setState { copy(isLoading = false) }
            }
        }
    }
}

package com.manager1700.soccer.ui.feature_add_edit_match

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.models.AttendanceInfo
import com.manager1700.soccer.domain.models.LineupScheme
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditMatchViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
    AddEditMatchContract.Event,
    AddEditMatchContract.State,
    AddEditMatchContract.Effect
>() {
    
    override fun createInitialState(): AddEditMatchContract.State {
        return AddEditMatchContract.State()
    }
    
    override fun handleEvent(event: AddEditMatchContract.Event) {
        when (event) {
            is AddEditMatchContract.Event.BackClicked -> handleBackClicked()
            is AddEditMatchContract.Event.SaveClicked -> handleSaveClicked()
            is AddEditMatchContract.Event.TypeChanged -> handleTypeChanged(event.type)
            is AddEditMatchContract.Event.OpponentChanged -> handleOpponentChanged(event.opponent)
            is AddEditMatchContract.Event.LineupSchemeChanged -> handleLineupSchemeChanged(event.scheme)
            is AddEditMatchContract.Event.DateChanged -> handleDateChanged(event.date)
            is AddEditMatchContract.Event.StartTimeChanged -> handleStartTimeChanged(event.time)
            is AddEditMatchContract.Event.EndTimeChanged -> handleEndTimeChanged(event.time)
            is AddEditMatchContract.Event.VenueChanged -> handleVenueChanged(event.venue)
            is AddEditMatchContract.Event.NoteChanged -> handleNoteChanged(event.note)
        }
    }
    
    fun initializeWithMatch(match: Match?, isEditMode: Boolean) {
        if (match != null && isEditMode) {
            setState {
                copy(
                    match = match,
                    isEditMode = true,
                    type = match.type,
                    opponent = match.opponent,
                    lineupScheme = match.lineupScheme.title,
                    date = match.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    startTime = match.startDateTime.toString(),
                    endTime = match.endDateTime.toString(),
                    venue = match.place,
                    note = match.note
                )
            }
        } else {
            setState {
                copy(
                    match = null,
                    isEditMode = false,
                    type = "",
                    opponent = "",
                    lineupScheme = "",
                    date = "",
                    startTime = "",
                    endTime = "",
                    venue = "",
                    note = ""
                )
            }
        }
    }
    
    fun loadMatchById(matchId: Int) {
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }
                val match = repository.getMatchById(matchId)
                setState {
                    copy(
                        match = match,
                        isEditMode = true,
                        isLoading = false,
                        type = match.type,
                        opponent = match.opponent,
                        lineupScheme = match.lineupScheme.title,
                        date = match.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        startTime = match.startDateTime.toString(),
                        endTime = match.endDateTime.toString(),
                        venue = match.place,
                        note = match.note
                    )
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { AddEditMatchContract.Effect.ShowError("Failed to load match: ${e.message}") }
            }
        }
    }
    
    private fun handleBackClicked() {
        setEffect { AddEditMatchContract.Effect.NavigateBack }
    }
    
    private fun handleSaveClicked() {
        val currentState = viewState.value
        
        // Validate required fields
        if (currentState.type.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please enter match type") }
            return
        }
        
        if (currentState.opponent.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please enter opponent name") }
            return
        }
        
        if (currentState.lineupScheme.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please select lineup scheme") }
            return
        }
        
        if (currentState.date.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please select date") }
            return
        }
        
        if (currentState.startTime.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please select start time") }
            return
        }
        
        if (currentState.endTime.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please select end time") }
            return
        }
        
        if (currentState.venue.isBlank()) {
            setEffect { AddEditMatchContract.Effect.ShowError("Please enter venue") }
            return
        }
        
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }
                
                val match = createMatchFromState(currentState)
                
                if (currentState.isEditMode) {
                    repository.updateMatch(match)
                    setEffect { AddEditMatchContract.Effect.ShowSuccess("Match updated successfully") }
                } else {
                    repository.createMatch(match)
                    setEffect { AddEditMatchContract.Effect.ShowSuccess("Match created successfully") }
                }
                
                setState { copy(isLoading = false) }
                setEffect { AddEditMatchContract.Effect.NavigateBack }
                
            } catch (e: Exception) {
                Log.e("AddEditMatchViewModel", "Error saving match", e)
                setState { copy(isLoading = false) }
                setEffect { AddEditMatchContract.Effect.ShowError("Failed to save match: ${e.message}") }
            }
        }
    }
    
    private fun createMatchFromState(state: AddEditMatchContract.State): Match {
        val matchId = if (state.isEditMode) state.match?.id ?: 0 else 0

        // Parse date from string (format: "dd.MM.yyyy")
        val date = try {
            val parts = state.date.split(".")
            if (parts.size == 3) {
                LocalDate.of(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
            } else {
                LocalDate.now() // fallback to today
            }
        } catch (e: Exception) {
            LocalDate.now() // fallback to today
        }

        // Parse lineup scheme
        val lineupScheme = try {
            LineupScheme.valueOf("Lineup_${state.lineupScheme.replace("-", "_")}")
        } catch (e: Exception) {
            LineupScheme.Lineup_4_3_3 // fallback to default
        }

        // Preserve existing data when editing
        val existingMatch = state.match
        return Match(
            id = matchId,
            status = existingMatch?.status ?: SportEventStatus.Scheduled,
            date = date,
            lineupScheme = lineupScheme,
            opponent = state.opponent,
            startDateTime = LocalTime.parse(state.startTime),
            endDateTime = LocalTime.parse(state.endTime),
            type = state.type,
            note = state.note,
            place = state.venue,
            title = state.type, // Using type as title for now
            photos = existingMatch?.photos ?: emptyList(),
            plannedAttendance = existingMatch?.plannedAttendance ?: AttendanceInfo(emptyMap()),
            realAttendance = existingMatch?.realAttendance ?: AttendanceInfo(emptyMap())
        )
    }
    
    private fun handleTypeChanged(type: String) {
        setState { copy(type = type) }
    }
    
    private fun handleOpponentChanged(opponent: String) {
        setState { copy(opponent = opponent) }
    }
    
    private fun handleLineupSchemeChanged(scheme: String) {
        setState { copy(lineupScheme = scheme) }
    }
    
    private fun handleDateChanged(date: String) {
        setState { copy(date = date) }
    }
    
    private fun handleStartTimeChanged(time: String) {
        setState { copy(startTime = time) }
    }
    
    private fun handleEndTimeChanged(time: String) {
        setState { copy(endTime = time) }
    }
    
    private fun handleVenueChanged(venue: String) {
        setState { copy(venue = venue) }
    }
    
    private fun handleNoteChanged(note: String) {
        setState { copy(note = note) }
    }
}

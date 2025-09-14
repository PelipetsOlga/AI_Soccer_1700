package com.manager1700.soccer.ui.feature_add_edit_player

import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.PlayerStatus
import com.manager1700.soccer.domain.models.Position
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPlayerViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
    AddEditPlayerContract.Event,
    AddEditPlayerContract.State,
    AddEditPlayerContract.Effect
>() {
    
    override fun createInitialState(): AddEditPlayerContract.State {
        return AddEditPlayerContract.State()
    }
    
    fun initializeWithPlayer(player: Player?) {
        if (player != null) {
            setState {
                copy(
                    player = player,
                    isEditMode = true,
                    playerNumber = player.number.toString(),
                    position = player.position.key,
                    foot = player.foot.key,
                    attendance = player.attendance.toString(),
                    sessions = player.sessions.toString(),
                    fitness = player.fitness.toString(),
                    note = player.note
                )
            }
        } else {
            setState {
                copy(
                    player = null,
                    isEditMode = false,
                    playerNumber = "",
                    position = Position.Defender.key,
                    foot = Foot.Right.key,
                    attendance = "0",
                    sessions = "0",
                    fitness = "100",
                    note = ""
                )
            }
        }
    }
    
    override fun handleEvent(event: AddEditPlayerContract.Event) {
        when (event) {
            is AddEditPlayerContract.Event.BackClicked -> handleBackClicked()
            is AddEditPlayerContract.Event.SaveClicked -> handleSaveClicked()
            is AddEditPlayerContract.Event.CancelClicked -> handleCancelClicked()
            is AddEditPlayerContract.Event.PlayerNumberChanged -> handlePlayerNumberChanged(event.number)
            is AddEditPlayerContract.Event.PositionChanged -> handlePositionChanged(event.position)
            is AddEditPlayerContract.Event.FootChanged -> handleFootChanged(event.foot)
            is AddEditPlayerContract.Event.AttendanceChanged -> handleAttendanceChanged(event.attendance)
            is AddEditPlayerContract.Event.SessionsChanged -> handleSessionsChanged(event.sessions)
            is AddEditPlayerContract.Event.FitnessChanged -> handleFitnessChanged(event.fitness)
            is AddEditPlayerContract.Event.NoteChanged -> handleNoteChanged(event.note)
        }
    }
    
    private fun handleBackClicked() {
        setEffect { AddEditPlayerContract.Effect.NavigateBack }
    }
    
    private fun handleCancelClicked() {
        setEffect { AddEditPlayerContract.Effect.NavigateBack }
    }
    
    private fun handleSaveClicked() {
        val currentState = viewState.value
        
        // Validate input
        if (!validateInput(currentState)) {
            return
        }
        
        setState { copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                val player = createPlayerFromState(currentState)
                
                if (currentState.isEditMode) {
                    repository.updatePlayer(player)
                    setEffect { AddEditPlayerContract.Effect.ShowSuccess("Player updated successfully") }
                } else {
                    repository.createPlayer(player)
                    setEffect { AddEditPlayerContract.Effect.ShowSuccess("Player created successfully") }
                }
                
                setEffect { AddEditPlayerContract.Effect.NavigateToTeam }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { AddEditPlayerContract.Effect.ShowError("Failed to save player: ${e.message}") }
            }
        }
    }
    
    private fun validateInput(state: AddEditPlayerContract.State): Boolean {
        return when {
            state.playerNumber.isBlank() -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Player number is required") }
                false
            }
            state.playerNumber.toIntOrNull() == null -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Player number must be a valid number") }
                false
            }
            state.attendance.toIntOrNull() == null -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Attendance must be a valid number") }
                false
            }
            state.sessions.toIntOrNull() == null -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Sessions must be a valid number") }
                false
            }
            state.fitness.toIntOrNull() == null -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Fitness must be a valid number") }
                false
            }
            state.fitness.toIntOrNull()?.let { it < 0 || it > 100 } == true -> {
                setEffect { AddEditPlayerContract.Effect.ShowError("Fitness must be between 0 and 100") }
                false
            }
            else -> true
        }
    }
    
    private fun createPlayerFromState(state: AddEditPlayerContract.State): Player {
        val playerId = if (state.isEditMode) state.player?.id ?: -1 else -1
        
        return Player(
            id = playerId,
            number = state.playerNumber.toInt(),
            position = Position.values().first { it.key == state.position },
            foot = Foot.values().first { it.key == state.foot },
            attendance = state.attendance.toInt(),
            sessions = state.sessions.toInt(),
            fitness = state.fitness.toInt(),
            status = PlayerStatus.Active,
            note = state.note,
            dateOfInjury = null,
            noteOfInjury = null,
            imageUrl = null
        )
    }
    
    private fun handlePlayerNumberChanged(number: String) {
        setState { copy(playerNumber = number) }
    }
    
    private fun handlePositionChanged(position: String) {
        setState { copy(position = position) }
    }
    
    private fun handleFootChanged(foot: String) {
        setState { copy(foot = foot) }
    }
    
    private fun handleAttendanceChanged(attendance: String) {
        setState { copy(attendance = attendance) }
    }
    
    private fun handleSessionsChanged(sessions: String) {
        setState { copy(sessions = sessions) }
    }
    
    private fun handleFitnessChanged(fitness: String) {
        setState { copy(fitness = fitness) }
    }
    
    private fun handleNoteChanged(note: String) {
        setState { copy(note = note) }
    }
}

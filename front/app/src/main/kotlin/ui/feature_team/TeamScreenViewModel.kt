package com.manager1700.soccer.ui.feature_team

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.data.utils.ImageFileManager
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamScreenViewModel @Inject constructor(
    private val soccerRepository: SoccerRepository,
    private val imageFileManager: ImageFileManager
) : MviViewModel<
        TeamScreenContract.Event,
        TeamScreenContract.State,
        TeamScreenContract.Effect
        >() {

    override fun createInitialState(): TeamScreenContract.State {
        return TeamScreenContract.State()
    }

    init {
        loadPlayers()
    }

    override fun handleEvent(event: TeamScreenContract.Event) {
        when (event) {
            is TeamScreenContract.Event.BackClicked -> handleBackClicked()
            is TeamScreenContract.Event.SettingsClicked -> handleSettingsClicked()
            is TeamScreenContract.Event.AddPlayerClicked -> handleAddPlayerClicked()
            is TeamScreenContract.Event.RemovePlayerClicked -> handleRemovePlayerClicked(event.player)
            is TeamScreenContract.Event.EditPlayerClicked -> handleEditPlayerClicked(event.player)
            is TeamScreenContract.Event.SetActiveClicked -> handleSetActivePlayerClicked(event.player)
            is TeamScreenContract.Event.SetInjuredClicked -> handleSetInjuredPlayerClicked(event.player)
            is TeamScreenContract.Event.ConfirmRemovePlayer -> handleConfirmRemovePlayer()
            is TeamScreenContract.Event.CancelRemovePlayer -> handleCancelRemovePlayer()
            is TeamScreenContract.Event.ReloadPlayers -> loadPlayers()
            is TeamScreenContract.Event.InjuryDateChanged -> handleInjuryDateChanged(event.date)
            is TeamScreenContract.Event.ConfirmSetInjured -> handleConfirmSetInjured()
            is TeamScreenContract.Event.CancelSetInjured -> handleCancelSetInjured()
            is TeamScreenContract.Event.ConfirmSetActive -> handleConfirmSetActive()
            is TeamScreenContract.Event.CancelSetActive -> handleCancelSetActive()
        }
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                val players = soccerRepository.getAllPlayers()
                Log.d("blabla", "players=${players.count()}")
                players.forEach { Log.d("blabla", "$it") }
                setState { copy(players = players, isLoading = false) }
            } catch (e: Exception) {
                setState { copy(players = emptyList(), isLoading = false) }
            }
        }
    }

    private fun handleBackClicked() {
        setEffect { TeamScreenContract.Effect.NavigateBack }
    }

    private fun handleSettingsClicked() {
        setEffect { TeamScreenContract.Effect.NavigateToSettings }
    }

    private fun handleAddPlayerClicked() {
        setEffect { TeamScreenContract.Effect.NavigateToAddPlayer }
    }

    private fun handleRemovePlayerClicked(player: Player) {
        setState { 
            copy(
                showRemovePlayerDialog = true,
                playerToRemove = player
            ) 
        }
    }

    private fun handleEditPlayerClicked(player: Player) {
        setEffect { TeamScreenContract.Effect.NavigateToEditPlayer(player) }
    }

    private fun handleConfirmRemovePlayer() {
        val playerToRemove = viewState.value.playerToRemove
        if (playerToRemove != null) {
            viewModelScope.launch {
                try {
                    // Delete player's image file if it exists
                    playerToRemove.imageUrl?.let { imagePath ->
                        imageFileManager.deleteImage(imagePath)
                    }
                    
                    // Delete player from database
                    soccerRepository.deletePlayerById(playerToRemove.id)
                    
                    // Reload players to update the UI
                    loadPlayers()
                } catch (e: Exception) {
                    Log.e("TeamScreenViewModel", "Error removing player", e)
                }
            }
        }
        setState { 
            copy(
                showRemovePlayerDialog = false,
                playerToRemove = null
            ) 
        }
    }

    private fun handleCancelRemovePlayer() {
        setState { 
            copy(
                showRemovePlayerDialog = false,
                playerToRemove = null
            ) 
        }
    }

    private fun handleSetActivePlayerClicked(player: Player) {
        setState { 
            copy(
                showSetActiveDialog = true,
                playerToSetActive = player
            ) 
        }
    }

    private fun handleSetInjuredPlayerClicked(player: Player) {
        setState { 
            copy(
                showSetInjuredDialog = true,
                playerToSetInjured = player,
                injuryDate = ""
            ) 
        }
    }

    private fun handleInjuryDateChanged(date: String) {
        setState { copy(injuryDate = date) }
    }

    private fun handleConfirmSetInjured() {
        val playerToSetInjured = viewState.value.playerToSetInjured
        val injuryDate = viewState.value.injuryDate
        
        if (playerToSetInjured != null && injuryDate.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val updatedPlayer = playerToSetInjured.copy(
                        status = com.manager1700.soccer.domain.models.PlayerStatus.Injured,
                        dateOfInjury = injuryDate
                    )
                    soccerRepository.updatePlayer(updatedPlayer)
                    loadPlayers() // Reload to update UI
                } catch (e: Exception) {
                    Log.e("TeamScreenViewModel", "Error setting player injured", e)
                }
            }
        }
        
        setState { 
            copy(
                showSetInjuredDialog = false,
                playerToSetInjured = null,
                injuryDate = ""
            ) 
        }
    }

    private fun handleCancelSetInjured() {
        setState { 
            copy(
                showSetInjuredDialog = false,
                playerToSetInjured = null,
                injuryDate = ""
            ) 
        }
    }

    private fun handleConfirmSetActive() {
        val playerToSetActive = viewState.value.playerToSetActive
        if (playerToSetActive != null) {
            viewModelScope.launch {
                try {
                    val updatedPlayer = playerToSetActive.copy(
                        status = com.manager1700.soccer.domain.models.PlayerStatus.Active,
                        dateOfInjury = null
                    )
                    soccerRepository.updatePlayer(updatedPlayer)
                    loadPlayers() // Reload to update UI
                } catch (e: Exception) {
                    Log.e("TeamScreenViewModel", "Error setting player active", e)
                }
            }
        }
        
        setState { 
            copy(
                showSetActiveDialog = false,
                playerToSetActive = null
            ) 
        }
    }

    private fun handleCancelSetActive() {
        setState { 
            copy(
                showSetActiveDialog = false,
                playerToSetActive = null
            ) 
        }
    }

}

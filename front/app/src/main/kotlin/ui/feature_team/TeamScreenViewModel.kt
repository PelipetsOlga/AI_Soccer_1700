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
            is TeamScreenContract.Event.ConfirmRemovePlayer -> handleConfirmRemovePlayer()
            is TeamScreenContract.Event.CancelRemovePlayer -> handleCancelRemovePlayer()
            is TeamScreenContract.Event.ReloadPlayers -> loadPlayers()
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

}

package com.manager1700.soccer.ui.feature_team

import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for TeamScreen
 */
object TeamScreenContract {
    
    /**
     * UI State for the team screen
     */
    data class State(
        val isLoading: Boolean = false,
        val players: List<Player> = emptyList(),
        val showRemovePlayerDialog: Boolean = false,
        val playerToRemove: Player? = null
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SettingsClicked : Event()
        data object AddPlayerClicked : Event()
        data class RemovePlayerClicked(val player: Player) : Event()
        data object ConfirmRemovePlayer : Event()
        data object CancelRemovePlayer : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToSettings : Effect()
        data object NavigateToAddPlayer : Effect()
    }
}

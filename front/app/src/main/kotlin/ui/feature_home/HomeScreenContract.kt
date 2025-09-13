package com.manager1700.soccer.ui.feature_home

import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for HomeScreen
 */
object HomeScreenContract {
    
    /**
     * UI State for the home screen
     */
    data class State(
        val isLoading: Boolean = false
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SettingsClicked : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToSettings : Effect()
    }
}

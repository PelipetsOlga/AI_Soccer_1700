package com.manager1700.soccer.ui.feature_welcome

import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for WelcomeScreen
 */
object WelcomeScreenContract {
    
    /**
     * UI State for the welcome screen
     */
    data class State(
        val isLoading: Boolean = false,
        val showLogo: Boolean = false,
        val showContent: Boolean = false,
        val logoScale: Float = 1.0f,
        val logoAlpha: Float = 0f,
        val contentAlpha: Float = 0f
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object GetStartedClicked : Event()
        data object StartAnimation : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateToHome : Effect()
    }
}

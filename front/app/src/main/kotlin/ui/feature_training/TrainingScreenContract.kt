package com.manager1700.soccer.ui.feature_training

import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for TrainingScreen
 */
object TrainingScreenContract {
    
    /**
     * UI State for the training screen
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
        data object AddTrainingClicked : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToSettings : Effect()
        data object NavigateToAddTraining : Effect()
    }
}

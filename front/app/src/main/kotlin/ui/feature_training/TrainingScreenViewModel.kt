package com.manager1700.soccer.ui.feature_training

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrainingScreenViewModel @Inject constructor() : MviViewModel<
    TrainingScreenContract.Event,
    TrainingScreenContract.State,
    TrainingScreenContract.Effect
>() {
    
    override fun createInitialState(): TrainingScreenContract.State {
        return TrainingScreenContract.State()
    }
    
    override fun handleEvent(event: TrainingScreenContract.Event) {
        when (event) {
            is TrainingScreenContract.Event.BackClicked -> handleBackClicked()
            is TrainingScreenContract.Event.SettingsClicked -> handleSettingsClicked()
            is TrainingScreenContract.Event.AddTrainingClicked -> handleAddTrainingClicked()
        }
    }
    
    private fun handleBackClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateBack }
    }
    
    private fun handleSettingsClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateToSettings }
    }
    
    private fun handleAddTrainingClicked() {
        setEffect { TrainingScreenContract.Effect.NavigateToAddTraining }
    }
}

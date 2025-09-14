package com.manager1700.soccer.ui.feature_match

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchScreenViewModel @Inject constructor() : MviViewModel<
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
        }
    }
    
    private fun handleBackClicked() {
        setEffect { MatchScreenContract.Effect.NavigateBack }
    }
    
    private fun handleSettingsClicked() {
        setEffect { MatchScreenContract.Effect.NavigateToSettings }
    }
}

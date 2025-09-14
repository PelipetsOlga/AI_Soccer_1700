package com.manager1700.soccer.ui.feature_team

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamScreenViewModel @Inject constructor() : MviViewModel<
    TeamScreenContract.Event,
    TeamScreenContract.State,
    TeamScreenContract.Effect
>() {
    
    override fun createInitialState(): TeamScreenContract.State {
        return TeamScreenContract.State()
    }
    
    override fun handleEvent(event: TeamScreenContract.Event) {
        when (event) {
            is TeamScreenContract.Event.BackClicked -> handleBackClicked()
            is TeamScreenContract.Event.SettingsClicked -> handleSettingsClicked()
            is TeamScreenContract.Event.AddPlayerClicked -> handleAddPlayerClicked()
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
}

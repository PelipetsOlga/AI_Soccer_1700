package com.manager1700.soccer.ui.feature_home

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : MviViewModel<
    HomeScreenContract.Event,
    HomeScreenContract.State,
    HomeScreenContract.Effect
>() {
    
    override fun createInitialState(): HomeScreenContract.State {
        return HomeScreenContract.State()
    }
    
    override fun handleEvent(event: HomeScreenContract.Event) {
        when (event) {
            is HomeScreenContract.Event.SettingsClicked -> handleSettingsClicked()
        }
    }
    
    private fun handleSettingsClicked() {
        setEffect { HomeScreenContract.Effect.NavigateToSettings }
    }
}

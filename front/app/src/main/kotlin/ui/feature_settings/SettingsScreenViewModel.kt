package com.manager1700.soccer.ui.feature_settings

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor() : MviViewModel<
    SettingsScreenContract.Event,
    SettingsScreenContract.State,
    SettingsScreenContract.Effect
>() {
    
    override fun createInitialState(): SettingsScreenContract.State {
        return SettingsScreenContract.State()
    }
    
    override fun handleEvent(event: SettingsScreenContract.Event) {
        when (event) {
            is SettingsScreenContract.Event.BackClicked -> handleBackClicked()
        }
    }
    
    private fun handleBackClicked() {
        setEffect { SettingsScreenContract.Effect.NavigateBack }
    }
}

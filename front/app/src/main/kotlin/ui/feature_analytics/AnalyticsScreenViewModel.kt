package com.manager1700.soccer.ui.feature_analytics

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsScreenViewModel @Inject constructor() : MviViewModel<
    AnalyticsScreenContract.Event,
    AnalyticsScreenContract.State,
    AnalyticsScreenContract.Effect
>() {
    
    override fun createInitialState(): AnalyticsScreenContract.State {
        return AnalyticsScreenContract.State()
    }
    
    override fun handleEvent(event: AnalyticsScreenContract.Event) {
        when (event) {
            is AnalyticsScreenContract.Event.BackClicked -> handleBackClicked()
            is AnalyticsScreenContract.Event.SettingsClicked -> handleSettingsClicked()
        }
    }
    
    private fun handleBackClicked() {
        setEffect { AnalyticsScreenContract.Effect.NavigateBack }
    }
    
    private fun handleSettingsClicked() {
        setEffect { AnalyticsScreenContract.Effect.NavigateToSettings }
    }
}

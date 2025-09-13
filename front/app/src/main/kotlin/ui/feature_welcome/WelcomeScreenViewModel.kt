package com.manager1700.soccer.ui.feature_welcome

import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor() : MviViewModel<
    WelcomeScreenContract.Event,
    WelcomeScreenContract.State,
    WelcomeScreenContract.Effect
>() {
    
    override fun createInitialState(): WelcomeScreenContract.State {
        return WelcomeScreenContract.State()
    }
    
    override fun handleEvent(event: WelcomeScreenContract.Event) {
        when (event) {
            is WelcomeScreenContract.Event.GetStartedClicked -> {
                handleGetStartedClicked()
            }
        }
    }
    
    private fun handleGetStartedClicked() {
        setState { copy(isLoading = true) }
        setEffect { WelcomeScreenContract.Effect.NavigateToHome }
    }
}

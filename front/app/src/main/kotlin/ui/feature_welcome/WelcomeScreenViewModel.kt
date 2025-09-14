package com.manager1700.soccer.ui.feature_welcome

import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.data.preferences.AppPreferences
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : MviViewModel<
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
            is WelcomeScreenContract.Event.StartAnimation -> {
                startAnimationSequence()
            }
        }
    }
    
    private fun handleGetStartedClicked() {
        setState { copy(isLoading = true) }
        
        viewModelScope.launch {
            // Initialize app settings and data
            initializeApp()
            
            // Mark first launch as completed
            appPreferences.setFirstLaunchCompleted()
            appPreferences.setInitialized()
            
            setEffect { WelcomeScreenContract.Effect.NavigateToHome }
        }
    }
    
    private suspend fun initializeApp() {
        // Initialize empty lists and settings
        // This is where you would initialize your database with default values
        // For now, we'll just add a small delay to simulate initialization
        delay(500)
        
        // TODO: Add actual initialization logic here:
        // - Initialize empty player list
        // - Initialize empty training list
        // - Initialize empty match list
        // - Set default settings
        // - Create default team structure
    }
    
    private fun startAnimationSequence() {
        // Start logo animation
        setState { copy(showLogo = true) }
        
        // After logo appears, start content animation
        viewModelScope.launch {
            delay(1400) // Wait for logo to appear
            setState { copy(showContent = true) }
        }
    }
}

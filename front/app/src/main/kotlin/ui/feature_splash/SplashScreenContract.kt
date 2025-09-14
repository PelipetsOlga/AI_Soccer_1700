package com.manager1700.soccer.ui.feature_splash

/**
 * MVI Contract for SplashScreen
 */
object SplashScreenContract {
    
    /**
     * UI State for the splash screen
     */
    data class UiState(
        val progress: Int = 1,
        val isLoading: Boolean = true,
        val shouldNavigateToHome: Boolean = false
    )
    
    /**
     * User intents/actions
     */
    sealed class Intent {
        data object StartLoading : Intent()
        data object NavigationCompleted : Intent()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class SideEffect {
        data object NavigateToWelcome : SideEffect()
        data object NavigateToHome : SideEffect()
    }
}

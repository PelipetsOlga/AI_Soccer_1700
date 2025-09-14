package com.manager1700.soccer.ui.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SplashScreenContract.UiState())
    val uiState: StateFlow<SplashScreenContract.UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SplashScreenContract.SideEffect>()
    val sideEffect: SharedFlow<SplashScreenContract.SideEffect> = _sideEffect.asSharedFlow()
    
    fun handleIntent(intent: SplashScreenContract.Intent) {
        when (intent) {
            is SplashScreenContract.Intent.StartLoading -> startLoadingProgress()
            is SplashScreenContract.Intent.NavigationCompleted -> resetNavigationFlag()
        }
    }
    
    private fun startLoadingProgress() {
        viewModelScope.launch {
            // Animate progress from 1% to 100% over 2 seconds
            for (progress in 1..1) {
                _uiState.value = _uiState.value.copy(progress = progress)
                delay(20) // 20ms * 100 = 2000ms (2 seconds)
            }
            
            // Check if this is first launch
            val isFirstLaunch = appPreferences.isFirstLaunch.first()
            
            // Mark loading as complete and trigger navigation
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                shouldNavigateToHome = true
            )
            
            // Emit side effect for navigation based on first launch
            if (isFirstLaunch) {
                _sideEffect.emit(SplashScreenContract.SideEffect.NavigateToWelcome)
            } else {
                _sideEffect.emit(SplashScreenContract.SideEffect.NavigateToHome)
            }
        }
    }
    
    private fun resetNavigationFlag() {
        _uiState.value = _uiState.value.copy(shouldNavigateToHome = false)
    }
}

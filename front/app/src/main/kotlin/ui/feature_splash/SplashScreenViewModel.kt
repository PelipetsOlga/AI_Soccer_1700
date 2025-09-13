package com.manager1700.soccer.ui.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {
    
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
            // Animate progress from 1% to 100% over 5 seconds
            for (progress in 1..100) {
                _uiState.value = _uiState.value.copy(progress = progress)
                delay(20) // 50ms * 100 = 5000ms (5 seconds)
            }
            
            // Mark loading as complete and trigger navigation
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                shouldNavigateToHome = true
            )
            
            // Emit side effect for navigation
            _sideEffect.emit(SplashScreenContract.SideEffect.NavigateToHome)
        }
    }
    
    private fun resetNavigationFlag() {
        _uiState.value = _uiState.value.copy(shouldNavigateToHome = false)
    }
}

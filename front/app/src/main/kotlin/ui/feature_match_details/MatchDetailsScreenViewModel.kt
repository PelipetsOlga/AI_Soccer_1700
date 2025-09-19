package com.manager1700.soccer.ui.feature_match_details

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsScreenViewModel @Inject constructor(
    private val repository: SoccerRepository
) : MviViewModel<
    MatchDetailsScreenContract.Event,
    MatchDetailsScreenContract.State,
    MatchDetailsScreenContract.Effect
>() {
    
    override fun createInitialState(): MatchDetailsScreenContract.State {
        return MatchDetailsScreenContract.State()
    }
    
    override fun handleEvent(event: MatchDetailsScreenContract.Event) {
        when (event) {
            is MatchDetailsScreenContract.Event.BackClicked -> handleBackClicked()
            is MatchDetailsScreenContract.Event.EditClicked -> handleEditClicked()
            is MatchDetailsScreenContract.Event.AttendanceClicked -> handleAttendanceClicked()
            is MatchDetailsScreenContract.Event.MarkAsClicked -> handleMarkAsClicked()
            is MatchDetailsScreenContract.Event.StatusChanged -> handleStatusChanged(event.status)
            is MatchDetailsScreenContract.Event.UploadPhotoClicked -> handleUploadPhotoClicked()
            is MatchDetailsScreenContract.Event.RemovePhotoClicked -> handleRemovePhotoClicked(event.photoIndex)
            is MatchDetailsScreenContract.Event.ImageSelected -> handleImageSelected(event.imageUri)
        }
    }
    
    fun loadMatchById(matchId: Int) {
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }
                val match = repository.getMatchById(matchId)
                setState { 
                    copy(
                        match = match,
                        photos = match.photos ?: emptyList(),
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                Log.e("MatchDetailsScreenViewModel", "Error loading match", e)
                setState { copy(isLoading = false) }
                setEffect { MatchDetailsScreenContract.Effect.ShowError("Failed to load match: ${e.message}") }
            }
        }
    }
    
    private fun handleBackClicked() {
        setEffect { MatchDetailsScreenContract.Effect.NavigateBack }
    }
    
    private fun handleEditClicked() {
        val currentState = viewState.value
        val match = currentState.match ?: return
        setEffect { MatchDetailsScreenContract.Effect.NavigateToEditMatch(match.id) }
    }
    
    private fun handleAttendanceClicked() {
        val currentState = viewState.value
        val match = currentState.match ?: return
        setEffect { MatchDetailsScreenContract.Effect.NavigateToMatchAttendance(match.id) }
    }
    
    private fun handleMarkAsClicked() {
        // TODO: Implement mark as functionality
    }
    
    private fun handleStatusChanged(status: SportEventStatus) {
        val currentState = viewState.value
        val match = currentState.match ?: return
        
        viewModelScope.launch {
            try {
                val updatedMatch = match.copy(status = status)
                repository.updateMatch(updatedMatch)
                setState { copy(match = updatedMatch) }
                setEffect { MatchDetailsScreenContract.Effect.ShowSuccess("Match status updated") }
            } catch (e: Exception) {
                Log.e("MatchDetailsScreenViewModel", "Error updating match status", e)
                setEffect { MatchDetailsScreenContract.Effect.ShowError("Failed to update match status") }
            }
        }
    }
    
    private fun handleUploadPhotoClicked() {
        // TODO: Implement photo upload functionality
    }
    
    private fun handleRemovePhotoClicked(photoIndex: Int) {
        val currentState = viewState.value
        val match = currentState.match ?: return
        
        viewModelScope.launch {
            try {
                val updatedPhotos = currentState.photos.toMutableList()
                if (photoIndex in updatedPhotos.indices) {
                    updatedPhotos.removeAt(photoIndex)
                    
                    val updatedMatch = match.copy(photos = updatedPhotos)
                    repository.updateMatch(updatedMatch)
                    setState { 
                        copy(
                            match = updatedMatch,
                            photos = updatedPhotos
                        ) 
                    }
                    setEffect { MatchDetailsScreenContract.Effect.ShowSuccess("Photo removed") }
                }
            } catch (e: Exception) {
                Log.e("MatchDetailsScreenViewModel", "Error removing photo", e)
                setEffect { MatchDetailsScreenContract.Effect.ShowError("Failed to remove photo") }
            }
        }
    }
    
    private fun handleImageSelected(imageUri: String) {
        val currentState = viewState.value
        val match = currentState.match ?: return
        
        viewModelScope.launch {
            try {
                val updatedPhotos = currentState.photos.toMutableList()
                updatedPhotos.add(imageUri)
                
                val updatedMatch = match.copy(photos = updatedPhotos)
                repository.updateMatch(updatedMatch)
                setState { 
                    copy(
                        match = updatedMatch,
                        photos = updatedPhotos
                    ) 
                }
                setEffect { MatchDetailsScreenContract.Effect.ShowSuccess("Photo added") }
            } catch (e: Exception) {
                Log.e("MatchDetailsScreenViewModel", "Error adding photo", e)
                setEffect { MatchDetailsScreenContract.Effect.ShowError("Failed to add photo") }
            }
        }
    }
}

package com.manager1700.soccer.ui.feature_training_details

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.data.utils.ImageFileManager
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrainingDetailsScreenViewModel @Inject constructor(
    private val repository: SoccerRepository,
    private val imageFileManager: ImageFileManager
) : MviViewModel<
        TrainingDetailsScreenContract.Event,
        TrainingDetailsScreenContract.State,
        TrainingDetailsScreenContract.Effect
        >() {

    override fun createInitialState(): TrainingDetailsScreenContract.State {
        return TrainingDetailsScreenContract.State()
    }

    fun loadTraining(trainingId: Int) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                val training = repository.getTrainingById(trainingId)
                setState { 
                    copy(
                        training = training,
                        photos = training.photos,
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                setState { 
                    copy(
                        isLoading = false,
                        errorMessage = "Failed to load training: ${e.message}"
                    ) 
                }
                setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to load training") }
            }
        }
    }

    override fun handleEvent(event: TrainingDetailsScreenContract.Event) {
        when (event) {
            is TrainingDetailsScreenContract.Event.BackClicked -> handleBackClicked()
            is TrainingDetailsScreenContract.Event.EditClicked -> handleEditClicked()
            is TrainingDetailsScreenContract.Event.AttendanceClicked -> handleAttendanceClicked()
            is TrainingDetailsScreenContract.Event.MarkAsClicked -> handleMarkAsClicked()
            is TrainingDetailsScreenContract.Event.StatusChanged -> handleStatusChanged(event.status)
            is TrainingDetailsScreenContract.Event.AddExerciseClicked -> handleAddExerciseClicked()
            is TrainingDetailsScreenContract.Event.ClearExercisesClicked -> handleClearExercisesClicked()
            is TrainingDetailsScreenContract.Event.EditExerciseClicked -> handleEditExerciseClicked(event.exerciseId)
            is TrainingDetailsScreenContract.Event.ExerciseTypeChanged -> handleExerciseTypeChanged(event.type)
            is TrainingDetailsScreenContract.Event.ExerciseDurationChanged -> handleExerciseDurationChanged(event.duration)
            is TrainingDetailsScreenContract.Event.ConfirmAddExercise -> handleConfirmAddExercise()
            is TrainingDetailsScreenContract.Event.CancelAddExercise -> handleCancelAddExercise()
            is TrainingDetailsScreenContract.Event.UploadPhotoClicked -> handleUploadPhotoClicked()
            is TrainingDetailsScreenContract.Event.RemovePhotoClicked -> handleRemovePhotoClicked(event.photoIndex)
            is TrainingDetailsScreenContract.Event.ImageSelected -> handleImageSelected(event.imageUri)
        }
    }

    private fun handleBackClicked() {
        setEffect { TrainingDetailsScreenContract.Effect.NavigateBack }
    }

    private fun handleEditClicked() {
        val currentState = viewState.value
        val training = currentState.training ?: return
        setEffect { TrainingDetailsScreenContract.Effect.NavigateToEditTraining(training.id) }
    }

    private fun handleAttendanceClicked() {
        setEffect { TrainingDetailsScreenContract.Effect.NavigateToAttendance }
    }

    private fun handleMarkAsClicked() {
        setEffect { TrainingDetailsScreenContract.Effect.ShowMarkAsDialog }
    }

    private fun handleStatusChanged(newStatus: com.manager1700.soccer.domain.models.SportEventStatus) {
        val currentState = viewState.value
        val training = currentState.training ?: return
        
        updateTrainingStatus(training.id, newStatus)
    }

    private fun handleAddExerciseClicked() {
        setState { copy(showAddExerciseDialog = true) }
    }

    private fun handleClearExercisesClicked() {
        // TODO: Implement clear exercises functionality
    }

    private fun handleEditExerciseClicked(exerciseId: String) {
        // TODO: Implement edit exercise functionality
    }

    private fun handleExerciseTypeChanged(type: String) {
        setState { copy(exerciseType = type) }
    }

    private fun handleExerciseDurationChanged(duration: String) {
        setState { copy(exerciseDuration = duration) }
    }

    private fun handleConfirmAddExercise() {
        val currentState = viewState.value
        val training = currentState.training ?: return
        val exerciseType = currentState.exerciseType.trim()
        val exerciseDuration = currentState.exerciseDuration.trim()

        if (exerciseType.isNotEmpty() && exerciseDuration.isNotEmpty()) {
            val durationInt = exerciseDuration.toIntOrNull()
            if (durationInt != null && durationInt > 0) {
                viewModelScope.launch {
                    try {
                        // Create new exercise
                        val newExercise = com.manager1700.soccer.domain.models.Exercise(
                            id = java.util.UUID.randomUUID().toString(),
                            title = exerciseType,
                            durationInMinutes = durationInt
                        )

                        // Add exercise to training
                        val updatedExercises = training.exercises.toMutableList()
                        updatedExercises.add(newExercise)

                        // Update training in database
                        val updatedTraining = training.copy(exercises = updatedExercises)
                        repository.updateTraining(updatedTraining)

                        // Update state
                        setState { 
                            copy(
                                training = updatedTraining,
                                showAddExerciseDialog = false,
                                exerciseType = "",
                                exerciseDuration = ""
                            ) 
                        }
                    } catch (e: Exception) {
                        Log.e("TrainingDetailsScreenViewModel", "Error adding exercise", e)
                        setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to add exercise") }
                    }
                }
            }
        }
    }

    private fun handleCancelAddExercise() {
        setState { 
            copy(
                showAddExerciseDialog = false,
                exerciseType = "",
                exerciseDuration = ""
            ) 
        }
    }

    private fun handleUploadPhotoClicked() {
        // TODO: Implement photo upload functionality
    }

    private fun handleRemovePhotoClicked(photoIndex: Int) {
        val currentState = viewState.value
        val training = currentState.training ?: return
        
        viewModelScope.launch {
            try {
                val updatedPhotos = currentState.photos.toMutableList()
                if (photoIndex in updatedPhotos.indices) {
                    val photoToRemove = updatedPhotos[photoIndex]
                    
                    // Delete photo file
                    imageFileManager.deleteImage(photoToRemove)
                    
                    // Remove from list
                    updatedPhotos.removeAt(photoIndex)
                    
                    // Update training in database
                    val updatedTraining = training.copy(photos = updatedPhotos)
                    repository.updateTraining(updatedTraining)
                    
                    // Update state
                    setState { 
                        copy(
                            training = updatedTraining,
                            photos = updatedPhotos
                        ) 
                    }
                }
            } catch (e: Exception) {
                Log.e("TrainingDetailsScreenViewModel", "Error removing photo", e)
                setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to remove photo") }
            }
        }
    }

    private fun handleImageSelected(imageUri: String) {
        viewModelScope.launch {
            try {
                val uri = Uri.parse(imageUri)
                val localImagePath = withContext(Dispatchers.IO) {
                    imageFileManager.saveImageFromUri(uri)
                }
                
                if (localImagePath != null) {
                    addPhoto(localImagePath)
                    Log.d("TrainingDetailsScreenViewModel", "Image saved to: $localImagePath")
                } else {
                    Log.e("TrainingDetailsScreenViewModel", "Failed to save image from URI: $imageUri")
                    setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to save image") }
                }
            } catch (e: Exception) {
                Log.e("TrainingDetailsScreenViewModel", "Error handling image selection", e)
                setEffect { TrainingDetailsScreenContract.Effect.ShowError("Error saving image: ${e.message}") }
            }
        }
    }

    fun addPhoto(photoPath: String) {
        val currentState = viewState.value
        val training = currentState.training ?: return
        
        if (currentState.photos.size >= 20) {
            setEffect { TrainingDetailsScreenContract.Effect.ShowError("Maximum 20 photos allowed") }
            return
        }
        
        viewModelScope.launch {
            try {
                val updatedPhotos = currentState.photos.toMutableList()
                updatedPhotos.add(photoPath)
                
                // Update training in database
                val updatedTraining = training.copy(photos = updatedPhotos)
                repository.updateTraining(updatedTraining)
                
                // Update state
                setState { 
                    copy(
                        training = updatedTraining,
                        photos = updatedPhotos
                    ) 
                }
            } catch (e: Exception) {
                Log.e("TrainingDetailsScreenViewModel", "Error adding photo", e)
                setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to add photo") }
            }
        }
    }

    private fun updateTrainingStatus(
        trainingId: Int,
        newStatus: com.manager1700.soccer.domain.models.SportEventStatus
    ) {
        viewModelScope.launch {
            try {
                // Get the current training
                val currentTraining = repository.getTrainingById(trainingId)

                // Create updated training with new status
                val updatedTraining = currentTraining.copy(status = newStatus)

                // Update in database
                repository.updateTraining(updatedTraining)

                // Update state
                setState { 
                    copy(training = updatedTraining)
                }
            } catch (e: Exception) {
                // Handle error - could show a snackbar or toast
                Log.e("TrainingDetailsScreenViewModel", "Error updating training status", e)
                setEffect { TrainingDetailsScreenContract.Effect.ShowError("Failed to update training status") }
            }
        }
    }
}

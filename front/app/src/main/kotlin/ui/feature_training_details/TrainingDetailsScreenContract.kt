package com.manager1700.soccer.ui.feature_training_details

import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for TrainingDetailsScreen
 */
object TrainingDetailsScreenContract {
    
    /**
     * UI State for the training details screen
     */
    data class State(
        val isLoading: Boolean = false,
        val training: Training? = null,
        val photos: List<String> = emptyList(),
        val errorMessage: String? = null
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object EditClicked : Event()
        data object AttendanceClicked : Event()
        data object MarkAsClicked : Event()
        data class StatusChanged(val status: com.manager1700.soccer.domain.models.SportEventStatus) : Event()
        data object AddExerciseClicked : Event()
        data object ClearExercisesClicked : Event()
        data class EditExerciseClicked(val exerciseId: String) : Event()
        data object UploadPhotoClicked : Event()
        data class RemovePhotoClicked(val photoIndex: Int) : Event()
        data class ImageSelected(val imageUri: String) : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class NavigateToEditTraining(val trainingId: Int) : Effect()
        data object NavigateToAttendance : Effect()
        data object ShowMarkAsDialog : Effect()
        data class ShowError(val message: String) : Effect()
    }
}

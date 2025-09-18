package com.manager1700.soccer.ui.feature_training

import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for TrainingScreen
 */
object TrainingScreenContract {
    
    enum class ViewType {
        LIST, CALENDAR
    }
    
    enum class FilterType {
        ALL, UPCOMING, PAST
    }
    
    /**
     * UI State for the training screen
     */
    data class State(
        val isLoading: Boolean = false,
        val trainings: List<Training> = emptyList(),
        val selectedViewType: ViewType = ViewType.LIST,
        val selectedFilterType: FilterType = FilterType.ALL
    ) : UiState
    
    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SettingsClicked : Event()
        data object AddTrainingClicked : Event()
        data class ViewTypeChanged(val viewType: ViewType) : Event()
        data class FilterTypeChanged(val filterType: FilterType) : Event()
        data class TrainingDetailsClicked(val trainingId: Int) : Event()
        data class TrainingAttendanceClicked(val trainingId: Int) : Event()
        data class TrainingMarkAsClicked(val trainingId: Int) : Event()
    }
    
    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToSettings : Effect()
        data object NavigateToAddTraining : Effect()
        data class NavigateToTrainingDetails(val trainingId: Int) : Effect()
        data class NavigateToTrainingAttendance(val trainingId: Int) : Effect()
        data class ShowMarkAsDialog(val trainingId: Int) : Effect()
    }
}

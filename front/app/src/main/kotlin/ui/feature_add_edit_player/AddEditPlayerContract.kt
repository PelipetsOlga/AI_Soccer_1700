package com.manager1700.soccer.ui.feature_add_edit_player

import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.Position
import com.manager1700.soccer.ui.base.UiEffect
import com.manager1700.soccer.ui.base.UiEvent
import com.manager1700.soccer.ui.base.UiState

/**
 * MVI Contract for AddEditPlayerScreen
 */
object AddEditPlayerContract {

    /**
     * UI State for the add/edit player screen
     */
    data class State(
        val isLoading: Boolean = false,
        val player: Player? = null,
        val isEditMode: Boolean = false,
        val playerName: String = "",
        val playerNumber: String = "",
        val position: Position? = null,
        val foot: Foot? = null,
        val fitness: String = "",
        val note: String = "",
        val imageUrl: String? = null,
        val errorMessage: String? = null,
        val isFormValid: Boolean = false,
    ) : UiState

    fun isCreatePlayerFormValid(state: State): Boolean {
        if (state.player != null) return true
        return state.playerName.isNotEmpty()
                && state.playerNumber.toIntOrNull() != null
                && state.fitness.toIntOrNull() != null
                && (state.fitness.toIntOrNull() ?: -1) >= 0
                && (state.fitness.toIntOrNull() ?: 200) <= 100
                && state.position != null
    }

    /**
     * User events/intents
     */
    sealed class Event : UiEvent {
        data object BackClicked : Event()
        data object SaveClicked : Event()
        data object CancelClicked : Event()
        data class PlayerNameChanged(val name: String) : Event()
        data class PlayerNumberChanged(val number: String) : Event()
        data class PositionChanged(val position: Position) : Event()
        data class FootChanged(val foot: Foot) : Event()
        data class FitnessChanged(val fitness: String) : Event()
        data class NoteChanged(val note: String) : Event()
        data object PhotoPickerClicked : Event()
        data class ImageSelected(val imageUri: String) : Event()
        data object DeletePhotoClicked : Event()
    }

    /**
     * Side effects that should be handled by the UI
     */
    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToTeam : Effect()
        data class ShowError(val message: String) : Effect()
        data object LaunchPhotoPicker : Effect()
    }
}

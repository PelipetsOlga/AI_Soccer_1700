package com.manager1700.soccer.ui.feature_add_edit_player

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.manager1700.soccer.data.utils.ImageFileManager
import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.PlayerStatus
import com.manager1700.soccer.domain.models.Position
import com.manager1700.soccer.domain.repo.SoccerRepository
import com.manager1700.soccer.ui.base.MviViewModel
import com.manager1700.soccer.ui.feature_add_edit_player.AddEditPlayerContract.isCreatePlayerFormValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditPlayerViewModel @Inject constructor(
    private val repository: SoccerRepository,
    private val imageFileManager: ImageFileManager
) : MviViewModel<
        AddEditPlayerContract.Event,
        AddEditPlayerContract.State,
        AddEditPlayerContract.Effect
        >() {

    override fun createInitialState(): AddEditPlayerContract.State {
        return AddEditPlayerContract.State()
    }

    fun initializeWithPlayer(player: Player?, isEditMode: Boolean) {
        setState {
            copy(
                player = player,
                isEditMode = isEditMode,
                playerName = player?.name.orEmpty(),
                playerNumber = player?.number?.toString().orEmpty(),
                position = player?.position,
                foot = player?.foot,
                fitness = player?.fitness?.toString().orEmpty(),
                note = player?.note.orEmpty(),
                imageUrl = player?.imageUrl
            )
        }
    }

    fun initializeWithPlayerId(playerId: Int, isEditMode: Boolean) {
        viewModelScope.launch {
            try {
                val player = repository.getPlayerById(playerId)
                initializeWithPlayer(player, isEditMode)
            } catch (e: Exception) {
                Log.e("AddEditPlayerViewModel", "Error loading player with ID: $playerId", e)
                setEffect { AddEditPlayerContract.Effect.ShowError("Failed to load player data") }
            }
        }
    }

    override fun handleEvent(event: AddEditPlayerContract.Event) {
        when (event) {
            is AddEditPlayerContract.Event.BackClicked -> handleBackClicked()
            is AddEditPlayerContract.Event.SaveClicked -> handleSaveClicked()
            is AddEditPlayerContract.Event.CancelClicked -> handleCancelClicked()
            is AddEditPlayerContract.Event.PlayerNameChanged -> handlePlayerNameChanged(event.name)
            is AddEditPlayerContract.Event.PlayerNumberChanged -> handlePlayerNumberChanged(event.number)
            is AddEditPlayerContract.Event.PositionChanged -> handlePositionChanged(event.position)
            is AddEditPlayerContract.Event.FootChanged -> handleFootChanged(event.foot)
            is AddEditPlayerContract.Event.FitnessChanged -> handleFitnessChanged(event.fitness)
            is AddEditPlayerContract.Event.NoteChanged -> handleNoteChanged(event.note)
            is AddEditPlayerContract.Event.PhotoPickerClicked -> handlePhotoPickerClicked()
            is AddEditPlayerContract.Event.ImageSelected -> handleImageSelected(event.imageUri)
            is AddEditPlayerContract.Event.DeletePhotoClicked -> handleDeletePhotoClicked()
        }
    }

    private fun handleBackClicked() {
        setEffect { AddEditPlayerContract.Effect.NavigateBack }
    }

    private fun handleCancelClicked() {
        setEffect { AddEditPlayerContract.Effect.NavigateBack }
    }

    private fun handleSaveClicked() {
        val currentState = viewState.value
        Log.d("blabla", "handleSaveClicked")

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val player = createPlayerFromState(currentState)
                Log.d("blabla", "createPlayerFromState $player")

                if (currentState.isEditMode) {
                    Log.d("blabla", "update")
                    repository.updatePlayer(player)
                } else {
                    Log.d("blabla", "insert")
                    val newPlayerId = repository.createPlayer(player)
                    Log.d("blabla", "Created player with ID: $newPlayerId")
                }
                setState { copy(isLoading = false) }
                setEffect { AddEditPlayerContract.Effect.NavigateToTeam }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { AddEditPlayerContract.Effect.ShowError("Failed to save player: ${e.message}") }
            }
        }
    }

    private fun createPlayerFromState(state: AddEditPlayerContract.State): Player {
        val playerId = if (state.isEditMode) state.player?.id ?: 0 else 0

        return Player(
            id = playerId,
            name = state.playerName,
            number = state.playerNumber.toIntOrNull() ?: -1,
            position = state.position ?: Position.Defender,
            foot = state.foot ?: Foot.Right,
            fitness = state.fitness.toIntOrNull() ?: 100,
            status = PlayerStatus.Active,
            note = state.note,
            dateOfInjury = null,
            imageUrl = state.imageUrl,
        )
    }

    private fun handlePlayerNameChanged(name: String) {
        setState {
            copy(
                playerName = name,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(playerName = name))
            )
        }
    }

    private fun handlePlayerNumberChanged(number: String) {
        setState {
            copy(
                playerNumber = number,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(playerNumber = number))
            )
        }
    }

    private fun handlePositionChanged(position: Position) {
        setState {
            copy(
                position = position,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(position = position))
            )
        }
    }

    private fun handleFootChanged(foot: Foot) {
        setState {
            copy(
                foot = foot,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(foot = foot))
            )
        }
    }

    private fun handleFitnessChanged(fitness: String) {
        setState {
            copy(
                fitness = fitness,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(fitness = fitness))
            )
        }
    }

    private fun handleNoteChanged(note: String) {
        setState {
            copy(
                note = note,
                isFormValid = isCreatePlayerFormValid(state = viewState.value.copy(note = note))
            )
        }
    }

    private fun handlePhotoPickerClicked() {
        setEffect { AddEditPlayerContract.Effect.LaunchPhotoPicker }
    }

    private fun handleImageSelected(imageUri: String) {
        viewModelScope.launch {
            try {
                val uri = Uri.parse(imageUri)
                val localImagePath = withContext(Dispatchers.IO) {
                    imageFileManager.saveImageFromUri(uri)
                }
                
                if (localImagePath != null) {
                    setState { copy(imageUrl = localImagePath) }
                    Log.d("AddEditPlayerViewModel", "Image saved to: $localImagePath")
                } else {
                    Log.e("AddEditPlayerViewModel", "Failed to save image from URI: $imageUri")
                    setEffect { AddEditPlayerContract.Effect.ShowError("Failed to save image") }
                }
            } catch (e: Exception) {
                Log.e("AddEditPlayerViewModel", "Error handling image selection", e)
                setEffect { AddEditPlayerContract.Effect.ShowError("Error saving image: ${e.message}") }
            }
        }
    }

    private fun handleDeletePhotoClicked() {
        val currentImagePath = viewState.value.imageUrl
        if (currentImagePath != null) {
            // Delete the file from storage
            imageFileManager.deleteImage(currentImagePath)
        }
        setState { copy(imageUrl = null) }
    }
}

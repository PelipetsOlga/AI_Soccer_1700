package com.manager1700.soccer.ui.feature_add_edit_player

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.Position
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.statusBarTopPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlayerScreen(
    isEditMode: Boolean = false,
    navController: NavController,
    player: Player?,
    viewModel: AddEditPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setEvent(AddEditPlayerContract.Event.ImageSelected(it.toString()))
        }
    }

    // Initialize with player data
    LaunchedEffect(player) {
        viewModel.initializeWithPlayer(player, isEditMode)
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddEditPlayerContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }

                is AddEditPlayerContract.Effect.NavigateToTeam -> {
                    navController.popBackStack()
                }

                is AddEditPlayerContract.Effect.ShowError -> {
                    // Handle error display - could show a snackbar or toast
                }

                is AddEditPlayerContract.Effect.LaunchPhotoPicker -> {
                    photoPickerLauncher.launch("image/*")
                }
            }
        }
    }

    AddEditPlayerScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlayerScreenContent(
    state: AddEditPlayerContract.State,
    onEvent: (AddEditPlayerContract.Event) -> Unit
) {
    val title = if (state.isEditMode) {
        stringResource(R.string.edit_player_title)
    } else {
        stringResource(R.string.add_player_title)
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = title,
                showBackButton = true,
                showSettingsButton = false,
                onBackClick = { onEvent(AddEditPlayerContract.Event.BackClicked) },
                modifier = Modifier.statusBarTopPadding(),
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        AddEditPlayerContent(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
    }
}

@PreviewApp
@Composable
fun AddEditPlayerScreenPreview() {
    SoccerManagerTheme {
        AddEditPlayerScreenContent(
            state = AddEditPlayerContract.State(
                isEditMode = false,
                playerNumber = "",
                position = Position.Defender,
                foot = Foot.Right,
                fitness = "100",
                note = ""
            ),
            onEvent = {}
        )
    }
}

package com.manager1700.soccer.ui.feature_add_edit_match

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.statusBarTopPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMatchScreen(
    match: Match? = null,
    navController: NavController,
    matchId: Int? = null,
    viewModel: AddEditMatchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Initialize with match data
    LaunchedEffect(match, matchId) {
        if (matchId != null && match == null) {
            viewModel.loadMatchById(matchId)
        } else {
            viewModel.initializeWithMatch(match, match != null)
        }
    }

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddEditMatchContract.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
                is AddEditMatchContract.Effect.NavigateToMatchDetails -> {
                    // TODO: Navigate to match details
                }
                is AddEditMatchContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }
                is AddEditMatchContract.Effect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = if (state.isEditMode) stringResource(R.string.edit_match_title) else stringResource(R.string.add_match_title),
                showBackButton = true,
                onBackClick = { viewModel.setEvent(AddEditMatchContract.Event.BackClicked) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = colorBlack
    ) { paddingValues ->
        AddEditMatchContent(
            state = state,
            onEvent = { viewModel.setEvent(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .statusBarTopPadding()
        )
    }
}

@PreviewApp
@Composable
fun AddEditMatchScreenPreview() {
    SoccerManagerTheme {
        // Preview with sample data
    }
}

package com.manager1700.soccer.ui.feature_team

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manager1700.soccer.R
import com.manager1700.soccer.Screen
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.feature_team.compose.PlayerCard
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    mainNavController: NavController,
    bottomNavController: NavController,
    viewModel: TeamScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TeamScreenContract.Effect.NavigateBack -> {
                    bottomNavController.popBackStack()
                }

                is TeamScreenContract.Effect.NavigateToSettings -> {
                    mainNavController.navigate(Screen.Settings.route)
                }

                is TeamScreenContract.Effect.NavigateToAddPlayer -> {
                    mainNavController.navigate(Screen.AddPlayer.route)
                }
            }
        }
    }

    TeamScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreenContent(
    state: TeamScreenContract.State,
    onEvent: (TeamScreenContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(R.string.team_title),
                showBackButton = true,
                showSettingsButton = true,
                onBackClick = { onEvent(TeamScreenContract.Event.BackClicked) },
                onSettingsClick = { onEvent(TeamScreenContract.Event.SettingsClicked) }
            )
        },
        containerColor = colorBlack
    ) { paddingValues ->
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
//                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.players.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.team_empty_list),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(state.players) { player ->
                        PlayerCard(player)
                    }
                }
            }

            // Add Player Button
            if (state.isLoading.not()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    PrimaryButton(
                        onClick = { onEvent(TeamScreenContract.Event.AddPlayerClicked) },
                        text = stringResource(R.string.add_player),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@PreviewApp
@Composable
fun TeamScreenContentPreview() {
    SoccerManagerTheme {
        TeamScreenContent(
            state = TeamScreenContract.State(
//                isLoading = true,
                players = listOf(
                    Player.TEST_1,
                    Player.TEST_1,
//                    Player.EMPTY.copy(name = "Bob"),
//                    Player.EMPTY.copy(name = "Dan"),
                )
            ),
            onEvent = {}
        )
    }
}

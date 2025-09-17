package com.manager1700.soccer.ui.feature_team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import android.app.DatePickerDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.manager1700.soccer.ui.components.SetInjuredDialog
import com.manager1700.soccer.ui.components.Toolbar
import com.manager1700.soccer.ui.feature_team.compose.PlayerCard
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.statusBarTopPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    mainNavController: NavController,
    bottomNavController: NavController,
    viewModel: TeamScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    // Reload players when screen is composed (when user returns from other screens)
    LaunchedEffect(Unit) {
        viewModel.setEvent(TeamScreenContract.Event.ReloadPlayers)
    }

    // Show Date Picker
    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                    val formattedDate = selectedDate.format(formatter)
                    viewModel.setEvent(TeamScreenContract.Event.InjuryDateChanged(formattedDate))
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
            showDatePicker = false
        }
    }

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

                is TeamScreenContract.Effect.NavigateToEditPlayer -> {
                    mainNavController.navigate("edit_player_screen/${effect.player.id}")
                }
            }
        }
    }

    TeamScreenContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )

    // Set Injured Dialog
    if (state.showSetInjuredDialog && state.playerToSetInjured != null) {
        val player = state.playerToSetInjured!!
        SetInjuredDialog(
            player = player,
            injuryDate = state.injuryDate,
            injuryNote = state.injuryNote,
            onInjuryDateChanged = { viewModel.setEvent(TeamScreenContract.Event.InjuryDateChanged(it)) },
            onInjuryNoteChanged = { viewModel.setEvent(TeamScreenContract.Event.InjuryNoteChanged(it)) },
            onDatePickerClick = {
                showDatePicker = true
            },
            onConfirm = { viewModel.setEvent(TeamScreenContract.Event.ConfirmSetInjured) },
            onCancel = { viewModel.setEvent(TeamScreenContract.Event.CancelSetInjured) }
        )
    }
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
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 16.dp)
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
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(state.players) { player ->
                        PlayerCard(player, onEvent)
                    }
                }
            }

            // Add Player Button - always visible at bottom
            if (state.isLoading.not()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
        
        // Confirmation Dialog
        if (state.showRemovePlayerDialog) {
            AlertDialog(
                onDismissRequest = { onEvent(TeamScreenContract.Event.CancelRemovePlayer) },
                title = {
                    Text(
                        text = "Are you sure you want to remove this player?",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = { onEvent(TeamScreenContract.Event.ConfirmRemovePlayer) }
                    ) {
                        Text("YES")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onEvent(TeamScreenContract.Event.CancelRemovePlayer) }
                    ) {
                        Text("NO")
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                textContentColor = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}

@PreviewApp
@Composable
fun TeamScreenContentPreview() {
    SoccerManagerTheme {
        TeamScreenContent(
            state = TeamScreenContract.State(
                showRemovePlayerDialog = true,
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

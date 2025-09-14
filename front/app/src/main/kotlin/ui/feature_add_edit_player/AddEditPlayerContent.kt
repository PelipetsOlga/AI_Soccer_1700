package com.manager1700.soccer.ui.feature_add_edit_player

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.components.Card
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.components.input.FitnessInputField
import com.manager1700.soccer.ui.components.input.FootInputField
import com.manager1700.soccer.ui.components.input.NameInputField
import com.manager1700.soccer.ui.components.input.NumberInputField
import com.manager1700.soccer.ui.components.input.PlayerNoteInputField
import com.manager1700.soccer.ui.components.input.PositionInputField
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun AddEditPlayerContent(
    state: AddEditPlayerContract.State,
    onEvent: (AddEditPlayerContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Player's data card
        Card(
            title = "Player's data",
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                NameInputField(
                    value = state.playerName,
                    onValueChange = { onEvent(AddEditPlayerContract.Event.PlayerNameChanged(it)) },
                )

                NumberInputField(
                    value = state.playerNumber,
                    onValueChange = { onEvent(AddEditPlayerContract.Event.PlayerNumberChanged(it)) },
                )

                PositionInputField(
                    value = if (state.position != null) stringResource(state.position.fullNameId) else "",
                    onClick = { onEvent(AddEditPlayerContract.Event.PositionChanged(it)) },
                )

                FootInputField(
                    value = if (state.foot != null) stringResource(state.foot.titleId) else "",
                    onClick = { onEvent(AddEditPlayerContract.Event.FootChanged(it)) },
                )

                FitnessInputField(
                    value = state.fitness,
                    onValueChange = { onEvent(AddEditPlayerContract.Event.FitnessChanged(it)) },
                )

                PlayerNoteInputField(
                    value = state.note,
                    onValueChange = { onEvent(AddEditPlayerContract.Event.NoteChanged(it)) },
                )

                // Position
                Text("Position: ${state.position}")


            }
        }


        PrimaryButton(
            onClick = { onEvent(AddEditPlayerContract.Event.SaveClicked) },
            text = stringResource(R.string.btn_save),
            modifier = Modifier,
            enabled = state.isFormValid,
        )

        SmallGreyButton(
            onClick = { onEvent(AddEditPlayerContract.Event.CancelClicked) },
            text = stringResource(R.string.btn_cancel),
            modifier = Modifier
        )
    }
}

@PreviewApp
@Composable
fun AddEditPlayerContentPreview() {
    SoccerManagerTheme {
        AddEditPlayerContent(
            state = AddEditPlayerContract.State(
                isEditMode = false,
                playerName = "",
                playerNumber = "",
                position = null,
                foot = null,
                fitness = "100",
                note = ""
            ),
            onEvent = {}
        )
    }
}
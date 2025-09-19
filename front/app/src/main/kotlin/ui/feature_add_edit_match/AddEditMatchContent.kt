package com.manager1700.soccer.ui.feature_add_edit_match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.PrimaryButton
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.components.input.DatePickerField
import com.manager1700.soccer.ui.components.input.TimePickerField
import com.manager1700.soccer.ui.components.input.TrainingNoteInputField
import com.manager1700.soccer.ui.components.input.VenueInputField
import com.manager1700.soccer.ui.components.input.MatchTypeInputField
import com.manager1700.soccer.ui.components.input.MatchOpponentInputField
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun AddEditMatchContent(
    state: AddEditMatchContract.State,
    onEvent: (AddEditMatchContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Match data card
        AppCard(
            title = "Match Data",
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                MatchTypeInputField(
                    value = state.type,
                    onValueChange = { onEvent(AddEditMatchContract.Event.TypeChanged(it)) },
                )

                MatchOpponentInputField(
                    value = state.opponent,
                    onValueChange = { onEvent(AddEditMatchContract.Event.OpponentChanged(it)) },
                )

                DatePickerField(
                    value = state.date,
                    onValueChange = { onEvent(AddEditMatchContract.Event.DateChanged(it)) },
                    onDatePickerClick = { onEvent(AddEditMatchContract.Event.DatePickerClicked) },
                )

                TimePickerField(
                    value = state.startTime,
                    onValueChange = { onEvent(AddEditMatchContract.Event.StartTimeChanged(it)) },
                    onTimePickerClick = { onEvent(AddEditMatchContract.Event.StartTimePickerClicked) },
                    label = stringResource(R.string.match_start_time_title),
                    placeholder = stringResource(R.string.match_start_time_hint),
                )

                TimePickerField(
                    value = state.endTime,
                    onValueChange = { onEvent(AddEditMatchContract.Event.EndTimeChanged(it)) },
                    onTimePickerClick = { onEvent(AddEditMatchContract.Event.EndTimePickerClicked) },
                    label = stringResource(R.string.match_end_time_title),
                    placeholder = stringResource(R.string.match_end_time_hint),
                )

                VenueInputField(
                    value = state.venue,
                    onValueChange = { onEvent(AddEditMatchContract.Event.VenueChanged(it)) },
                )

                TrainingNoteInputField(
                    value = state.note,
                    onValueChange = { onEvent(AddEditMatchContract.Event.NoteChanged(it)) },
                )
            }
        }

        PrimaryButton(
            onClick = { onEvent(AddEditMatchContract.Event.SaveClicked) },
            text = stringResource(R.string.match_save),
            modifier = Modifier,
            enabled = state.isFormValid,
        )

        SmallGreyButton(
            onClick = { onEvent(AddEditMatchContract.Event.BackClicked) },
            text = stringResource(R.string.match_cancel),
            modifier = Modifier
        )
    }
}

@PreviewApp
@Composable
fun AddEditMatchContentPreview() {
    SoccerManagerTheme {
        AddEditMatchContent(
            state = AddEditMatchContract.State(),
            onEvent = {}
        )
    }
}

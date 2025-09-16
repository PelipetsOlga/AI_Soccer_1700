package com.manager1700.soccer.ui.feature_add_edit_training

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
import com.manager1700.soccer.ui.components.input.TypePickerField
import com.manager1700.soccer.ui.components.input.VenuePickerField
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun AddEditTrainingContent(
    state: AddEditTrainingContract.State,
    onEvent: (AddEditTrainingContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Training data card
        AppCard(
            title = "Training Data",
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TypePickerField(
                    value = state.type,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.TypeChanged(it)) },
                    onTypePickerClick = { onEvent(AddEditTrainingContract.Event.TypePickerClicked) },
                )

                DatePickerField(
                    value = state.date,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.DateChanged(it)) },
                    onDatePickerClick = { onEvent(AddEditTrainingContract.Event.DatePickerClicked) },
                )

                TimePickerField(
                    value = state.startTime,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.StartTimeChanged(it)) },
                    onTimePickerClick = { onEvent(AddEditTrainingContract.Event.StartTimePickerClicked) },
                    label = stringResource(R.string.field_start_time),
                    placeholder = stringResource(R.string.tint_start_time),
                )

                TimePickerField(
                    value = state.endTime,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.EndTimeChanged(it)) },
                    onTimePickerClick = { onEvent(AddEditTrainingContract.Event.EndTimePickerClicked) },
                    label = stringResource(R.string.field_end_time),
                    placeholder = stringResource(R.string.tint_end_time),
                )

                VenuePickerField(
                    value = state.venue,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.VenueChanged(it)) },
                    onVenuePickerClick = { onEvent(AddEditTrainingContract.Event.VenuePickerClicked) },
                )

                TrainingNoteInputField(
                    value = state.note,
                    onValueChange = { onEvent(AddEditTrainingContract.Event.NoteChanged(it)) },
                )
            }
        }

        PrimaryButton(
            onClick = { onEvent(AddEditTrainingContract.Event.SaveClicked) },
            text = stringResource(R.string.btn_save),
            modifier = Modifier,
            enabled = state.isFormValid,
        )

        SmallGreyButton(
            onClick = { onEvent(AddEditTrainingContract.Event.CancelClicked) },
            text = stringResource(R.string.btn_cancel),
            modifier = Modifier
        )
    }
}

@PreviewApp
@Composable
fun AddEditTrainingContentPreview() {
    SoccerManagerTheme {
        AddEditTrainingContent(
            state = AddEditTrainingContract.State(
                isEditMode = false,
                type = "",
                date = "",
                startTime = "",
                endTime = "",
                venue = "",
                note = ""
            ),
            onEvent = {}
        )
    }
}

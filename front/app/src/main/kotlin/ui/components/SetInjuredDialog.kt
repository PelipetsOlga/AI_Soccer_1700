package com.manager1700.soccer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.ui.components.input.DatePickerField
import com.manager1700.soccer.ui.components.input.PlayerNoteInputField
import com.manager1700.soccer.ui.theme.colorWhite

@Composable
fun SetInjuredDialog(
    player: Player,
    injuryDate: String,
    injuryNote: String,
    onInjuryDateChanged: (String) -> Unit,
    onInjuryNoteChanged: (String) -> Unit,
    onDatePickerClick: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "Set ${player.name} as Injured",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = Montserrat
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DatePickerField(
                    value = injuryDate,
                    onValueChange = onInjuryDateChanged,
                    onDatePickerClick = onDatePickerClick
                )
                
                PlayerNoteInputField(
                    value = injuryNote,
                    onValueChange = onInjuryNoteChanged
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = injuryDate.isNotEmpty()
            ) {
                Text(
                    text = "YES",
                    color = colorWhite,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = "NO",
                    color = colorWhite,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}

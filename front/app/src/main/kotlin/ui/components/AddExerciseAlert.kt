package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.cardVeryBigClipShape

@Composable
fun AddExerciseAlert(
    exerciseType: String,
    exerciseDuration: String,
    onExerciseTypeChanged: (String) -> Unit,
    onExerciseDurationChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    isEditMode: Boolean = false,
    onRemove: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = if (isEditMode) stringResource(R.string.edit_exercise_title) else stringResource(R.string.add_exercise_title),
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
                // Exercise Type Input Field
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.add_exercise_type_title).uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorWhite,
                        fontFamily = Montserrat,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = exerciseType,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1000) {
                                onExerciseTypeChanged(newValue)
                            }
                        },
                        maxLines = 1,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.add_exercise_type_hint),
                                fontSize = 12.sp,
                                minLines = 1,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic,
                                color = colorBlack.copy(alpha = 0.6f),
                                fontFamily = Montserrat,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                        colors = getInputColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(cardVeryBigClipShape)
                            .background(colorGrey_89)
                    )
                }

                // Exercise Duration Input Field
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.add_exercise_duration_title).uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorWhite,
                        fontFamily = Montserrat,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = exerciseDuration,
                        onValueChange = { newValue ->
                            // Only allow numeric input
                            if (newValue.all { it.isDigit() }) {
                                onExerciseDurationChanged(newValue)
                            }
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.add_exercise_duration_hint),
                                fontSize = 12.sp,
                                minLines = 1,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic,
                                color = colorBlack.copy(alpha = 0.6f),
                                fontFamily = Montserrat,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                        colors = getInputColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(cardVeryBigClipShape)
                            .background(colorGrey_89)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = exerciseType.isNotEmpty() && exerciseDuration.isNotEmpty() && exerciseDuration.toIntOrNull() != null
            ) {
                Text(
                    text = stringResource(R.string.add_exercise_save),
                    color = colorWhite,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            if (isEditMode && onRemove != null) {
                TextButton(onClick = onRemove) {
                    Text(
                        text = stringResource(R.string.edit_exercise_remove),
                        color = colorWhite,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                TextButton(onClick = onCancel) {
                    Text(
                        text = stringResource(R.string.add_exercise_cancel),
                        color = colorWhite,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun getInputColors() = TextFieldDefaults.outlinedTextFieldColors(
    textColor = colorBlack,
    cursorColor = colorBlack,
    focusedBorderColor = colorGrey_89,
    unfocusedBorderColor = colorGrey_89,
    focusedLabelColor = colorWhite,
    unfocusedLabelColor = colorWhite
)

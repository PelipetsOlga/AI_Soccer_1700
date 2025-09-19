package com.manager1700.soccer.ui.feature_training_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Exercise
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorGrey_af
import com.manager1700.soccer.ui.theme.colorGrey_ce
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.cardBigClipShape
import com.manager1700.soccer.ui.utils.cardClipShape

@Composable
fun TrainingPlanList(
    training: Training,
    onEvent: (TrainingDetailsScreenContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardBigClipShape)
            .background(colorGrey_ce)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.training_details_plan_list).uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colorGrey_2b,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (training.exercises.isNotEmpty()) {
                training.exercises.forEach { exercise ->
                    ExerciseView(exercise = exercise, onEvent = onEvent)
                }
            } else {
                Text(
                    text = stringResource(R.string.training_details_no_exercises),
                    fontSize = 14.sp,
                    color = colorGrey_89,
                    fontFamily = Montserrat,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SmallGreyButton(
                    text = stringResource(R.string.training_details_add_exercise),
                    onClick = { onEvent(TrainingDetailsScreenContract.Event.AddExerciseClicked) },
                    modifier = Modifier.weight(1f)
                )
                if (training.exercises.isNotEmpty()) {
                    SmallGreyButton(
                        text = stringResource(R.string.training_details_clear),
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.ClearExercisesClicked) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseView(
    exercise: Exercise,
    onEvent: (TrainingDetailsScreenContract.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardClipShape)
            .background(colorGrey_af)
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.training_details_exercise,
                    exercise.title
                ),
                fontSize = 12.sp,
                color = colorWhite,
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraLight,
            )
            Text(
                text = stringResource(
                    R.string.training_details_duration,
                    exercise.durationInMinutes
                ),
                fontSize = 12.sp,
                color = colorWhite,
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraLight,
            )
        }
        SmallGreyButton(
            text = stringResource(R.string.training_details_edit),
            onClick = {
                onEvent(
                    TrainingDetailsScreenContract.Event.EditExerciseClicked(
                        exercise.id
                    )
                )
            }
        )
    }
}

@PreviewApp
@Composable
private fun TrainingPlanListPreview() {
    SoccerManagerTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGrey_2b)
                .padding(all = 16.dp)
        ) {
            TrainingPlanList(
                training = Training.TEST_1.copy(
                    exercises = listOf(
                        Exercise.EXERCISE_1,
                        Exercise.EXERCISE_2,
                    )
                ),
                onEvent = {},
            )
        }
    }
}
package com.manager1700.soccer.ui.feature_training_details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Exercise
import com.manager1700.soccer.domain.models.FutureAttendance
import com.manager1700.soccer.domain.models.PastAttendance
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.components.SportEventStatusChip
import com.manager1700.soccer.ui.components.input.PhotoPickerField
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_3b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import java.time.format.DateTimeFormatter

@Composable
fun TrainingDetailsScreenContent(
    paddingValues: PaddingValues,
    trainingId: Int,
    onEvent: (TrainingDetailsScreenContract.Event) -> Unit,
    viewModel: TrainingDetailsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setEvent(TrainingDetailsScreenContract.Event.ImageSelected(it.toString()))
        }
    }

    // Load training when screen is composed
    LaunchedEffect(trainingId) {
        viewModel.loadTraining(trainingId)
    }

    // Handle side effects (only non-navigation effects)
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TrainingDetailsScreenContract.Effect.ShowError -> {
                    // TODO: Show error message
                }
                // Navigation effects are handled by the main screen
                else -> {
                    // Other effects handled by main screen
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.training_details_loading),
                color = colorWhite,
                fontFamily = Montserrat
            )
        }
    } else if (state.training != null) {
        TrainingDetailsContent(
            state = state,
            onEvent = onEvent,
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            onPhotoPickerClick = { photoPickerLauncher.launch("image/*") }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.training_details_not_found),
                color = colorWhite,
                fontFamily = Montserrat
            )
        }
    }
}

@Composable
private fun TrainingDetailsContent(
    state: TrainingDetailsScreenContract.State,
    onEvent: (TrainingDetailsScreenContract.Event) -> Unit,
    onPhotoPickerClick: () -> Unit,
    modifier: Modifier
) {
    val training = state.training!!
    val isCompleted = training.status == SportEventStatus.Completed
    var showStatusDropdown by remember { mutableStateOf(false) }

    val formattedDate = training.date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
    val formattedTime = "${training.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
        training.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }"

    // Format attendance info
    val attendanceText = if (isCompleted) {
        val present = training.realAttendance.info[PastAttendance.Present] ?: 0
        val late = training.realAttendance.info[PastAttendance.Late] ?: 0
        val absent = training.realAttendance.info[PastAttendance.Absent] ?: 0
        val presentText = stringResource(R.string.training_details_attendance_present, present)
        val lateText = stringResource(R.string.training_details_attendance_late, late)
        val absentText = stringResource(R.string.training_details_attendance_absent, absent)
        stringResource(
            R.string.training_details_attendance_format,
            "$presentText, $lateText, $absentText"
        )
    } else {
        val going = training.plannedAttendance.info[FutureAttendance.Going] ?: 0
        val maybe = training.plannedAttendance.info[FutureAttendance.Maybe] ?: 0
        val notGoing = training.plannedAttendance.info[FutureAttendance.NotGoing] ?: 0
        val goingText = stringResource(R.string.training_details_attendance_going, going)
        val maybeText = stringResource(R.string.training_details_attendance_maybe, maybe)
        val notGoingText = stringResource(R.string.training_details_attendance_not_going, notGoing)
        stringResource(
            R.string.training_details_attendance_format,
            "$goingText, $maybeText, $notGoingText"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCard(
            title = training.type.uppercase()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header row with date, time, location and status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "$formattedDate | ${training.place}",
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )
                        Text(
                            text = formattedTime,
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )
                    }
                    SportEventStatusChip(training.status)
                }

                // Attendance info
                Text(
                    text = attendanceText,
                    fontSize = 12.sp,
                    color = colorGrey_89,
                    fontFamily = Montserrat
                )

                TrainingPlanList(
                    training = state.training,
                    onEvent = onEvent,
                )

                // Note section
                if (training.note.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.training_details_note, training.note),
                        fontSize = 14.sp,
                        color = colorWhite,
                        fontFamily = Montserrat
                    )
                }

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (state.photos.size < 20) {
                        // Show upload button
                        Box(
                            modifier = Modifier
                                .width(75.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorWhite)
                                .border(1.dp, colorGrey_3b, RoundedCornerShape(8.dp))
                                .clickable { onPhotoPickerClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.mipmap.ic_upload),
                                    contentDescription = "Upload photo",
                                )
                            }
                        }
                    }

                    state.photos.forEachIndexed { index, photoPath ->
                        PhotoPickerField(
                            imageUrl = photoPath,
                            onPhotoPickerClick = onPhotoPickerClick,
                            onDeletePhotoClick = {
                                onEvent(
                                    TrainingDetailsScreenContract.Event.RemovePhotoClicked(
                                        index
                                    )
                                )
                            },
                            showUploadButton = false,
                            modifier = Modifier.size(120.dp, 160.dp)
                        )
                    }
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallGreyButton(
                        text = stringResource(R.string.training_details_attendance),
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.AttendanceClicked) },
                        modifier = Modifier.weight(1f)
                    )
                    SmallGreyButton(
                        text = stringResource(R.string.training_details_edit),
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.EditClicked) },
                        modifier = Modifier.weight(1f)
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        SmallGreyButton(
                            text = stringResource(R.string.btn_mark_as),
                            onClick = { showStatusDropdown = true },
                            modifier = Modifier.fillMaxWidth()
                        )

                        DropdownMenu(
                            expanded = showStatusDropdown,
                            onDismissRequest = { showStatusDropdown = false },
                            modifier = Modifier.background(colorWhite)
                        ) {
                            SportEventStatus.entries.forEach { status ->
                                DropdownMenuItem(
                                    onClick = {
                                        onEvent(
                                            TrainingDetailsScreenContract.Event.StatusChanged(
                                                status
                                            )
                                        )
                                        showStatusDropdown = false
                                    }
                                ) {
                                    Text(
                                        text = stringResource(status.titleId),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = colorBlack,
                                        fontFamily = Montserrat,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@PreviewApp
@Composable
fun TrainingDetailsScreenContentPreview() {
    SoccerManagerTheme {
        TrainingDetailsContent(
            state = TrainingDetailsScreenContract.State(
                training = com.manager1700.soccer.domain.models.Training.TEST_1.copy(
                    exercises = listOf(
                        Exercise.EXERCISE_1,
                        Exercise.EXERCISE_2,
                    )
                ),
                photos = listOf("photo1.jpg", "photo2.jpg")
            ),
            onEvent = {},
            onPhotoPickerClick = {},
            Modifier,
        )
    }
}

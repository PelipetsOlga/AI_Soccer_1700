package com.manager1700.soccer.ui.feature_training_details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.FutureAttendance
import com.manager1700.soccer.domain.models.PastAttendance
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.components.SportEventStatusChip
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import java.io.File
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
                text = "Loading...",
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
                text = "Training not found",
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

    val formattedDate = training.date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
    val formattedTime = "${training.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
        training.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }"

    // Format attendance info
    val attendanceText = if (isCompleted) {
        val present = training.realAttendance.info[PastAttendance.Present] ?: 0
        val late = training.realAttendance.info[PastAttendance.Late] ?: 0
        val absent = training.realAttendance.info[PastAttendance.Absent] ?: 0
        "Attendance: Present - $present, Late - $late, Absent - $absent"
    } else {
        val going = training.plannedAttendance.info[FutureAttendance.Going] ?: 0
        val maybe = training.plannedAttendance.info[FutureAttendance.Maybe] ?: 0
        val notGoing = training.plannedAttendance.info[FutureAttendance.NotGoing] ?: 0
        "Attendance: Going - $going, Maybe - $maybe, Not Going - $notGoing"
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

                // Plan List Section
                AppCard(
                    title = "Plan List",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (training.exercises.isNotEmpty()) {
                            training.exercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Exercise: ${exercise.title}",
                                            fontSize = 14.sp,
                                            color = colorWhite,
                                            fontFamily = Montserrat
                                        )
                                        Text(
                                            text = "Duration: ${exercise.durationInMinutes} min",
                                            fontSize = 12.sp,
                                            color = colorGrey_89,
                                            fontFamily = Montserrat
                                        )
                                    }
                                    SmallGreyButton(
                                        text = "EDIT",
                                        onClick = { onEvent(TrainingDetailsScreenContract.Event.EditExerciseClicked(exercise.id)) }
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "No exercises added",
                                fontSize = 14.sp,
                                color = colorGrey_89,
                                fontFamily = Montserrat,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SmallGreyButton(
                                text = "ADD EXERCISE",
                                onClick = { onEvent(TrainingDetailsScreenContract.Event.AddExerciseClicked) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            SmallGreyButton(
                                text = "CLEAR",
                                onClick = { onEvent(TrainingDetailsScreenContract.Event.ClearExercisesClicked) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Note section
                if (training.note.isNotEmpty()) {
                    Text(
                        text = "Note: ${training.note}",
                        fontSize = 14.sp,
                        color = colorWhite,
                        fontFamily = Montserrat
                    )
                }

                // Photos section
                PhotosSection(
                    photos = state.photos,
                    onPhotoPickerClick = onPhotoPickerClick,
                    onRemovePhoto = { index -> onEvent(TrainingDetailsScreenContract.Event.RemovePhotoClicked(index)) }
                )

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallGreyButton(
                        text = "ATTENDANCE",
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.AttendanceClicked) },
                        modifier = Modifier.weight(1f)
                    )
                    SmallGreyButton(
                        text = "EDIT",
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.EditClicked) },
                        modifier = Modifier.weight(1f)
                    )
                    SmallGreyButton(
                        text = "MARK AS",
                        onClick = { onEvent(TrainingDetailsScreenContract.Event.MarkAsClicked) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotosSection(
    photos: List<String>,
    onPhotoPickerClick: () -> Unit,
    onRemovePhoto: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Photos",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorWhite,
            fontFamily = Montserrat
        )

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display existing photos
            photos.forEachIndexed { index, photoPath ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(File(photoPath))
                            .build(),
                        contentDescription = "Training photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorGrey_2b),
                        contentScale = ContentScale.Crop
                    )

                    // Remove button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(20.dp)
                            .background(colorBlack.copy(alpha = 0.7f), CircleShape)
                            .clickable { onRemovePhoto(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "×",
                            color = colorWhite,
                            fontSize = 12.sp,
                            fontFamily = Montserrat
                        )
                    }
                }
            }

            // Upload button (if less than 20 photos)
            if (photos.size < 20) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorGrey_2b)
                        .clickable { onPhotoPickerClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "📷",
                            fontSize = 24.sp
                        )
                        Text(
                            text = "UPLOAD",
                            fontSize = 10.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )
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
                training = com.manager1700.soccer.domain.models.Training.TEST_1,
                photos = listOf("photo1.jpg", "photo2.jpg")
            ),
            onEvent = {},
            onPhotoPickerClick = {},
            Modifier,
        )
    }
}

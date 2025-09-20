package com.manager1700.soccer.ui.feature_match_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.FutureAttendance
import com.manager1700.soccer.domain.models.PastAttendance
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.components.SportEventStatusChip
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import java.time.format.DateTimeFormatter

@Composable
fun MatchDetailsScreenContent(
    state: MatchDetailsScreenContract.State,
    onEvent: (MatchDetailsScreenContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.match_details_loading),
                fontSize = 16.sp,
                color = colorWhite,
                fontFamily = Montserrat
            )
        }
        return
    }

    val match = state.match
    if (match == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.match_details_not_found),
                fontSize = 16.sp,
                color = colorWhite,
                fontFamily = Montserrat
            )
        }
        return
    }

    var showStatusDropdown by remember { mutableStateOf(false) }
    val isCompleted = match.status == SportEventStatus.Completed

    val formattedDate = match.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    // Format attendance info
    val attendanceText = if (isCompleted) {
        val present = match.realAttendance.info[PastAttendance.Present] ?: 0
        val late = match.realAttendance.info[PastAttendance.Late] ?: 0
        val absent = match.realAttendance.info[PastAttendance.Absent] ?: 0
        stringResource(
            R.string.match_details_attendance_format,
            stringResource(R.string.match_details_attendance_present, present) + ", " +
            stringResource(R.string.match_details_attendance_late, late) + ", " +
            stringResource(R.string.match_details_attendance_absent, absent)
        )
    } else {
        val going = match.plannedAttendance.info[FutureAttendance.Going] ?: 0
        val maybe = match.plannedAttendance.info[FutureAttendance.Maybe] ?: 0
        val notGoing = match.plannedAttendance.info[FutureAttendance.NotGoing] ?: 0
        stringResource(
            R.string.match_details_attendance_format,
            stringResource(R.string.match_details_attendance_going, going) + ", " +
            stringResource(R.string.match_details_attendance_maybe, maybe) + ", " +
            stringResource(R.string.match_details_attendance_not_going, notGoing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Match Info Card
        AppCard(
            title = match.type.uppercase(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header row with title and status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.match_details_date, formattedDate),
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )

                        Text(
                            text = stringResource(R.string.match_details_time, 
                                match.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                match.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                            ),
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )

                        Text(
                            text = stringResource(R.string.match_details_opponent, match.opponent),
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(R.string.match_details_lineup, match.lineupScheme.title),
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )

                        Text(
                            text = stringResource(R.string.match_details_venue, match.place),
                            fontSize = 14.sp,
                            color = colorWhite,
                            fontFamily = Montserrat
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        SportEventStatusChip(match.status)
                    }
                }

                // Attendance info
                Text(
                    text = attendanceText,
                    fontSize = 12.sp,
                    color = colorGrey_89,
                    fontFamily = Montserrat
                )

                // Note
                if (match.note.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.match_details_note, match.note),
                        fontSize = 12.sp,
                        color = colorGrey_89,
                        fontFamily = Montserrat
                    )
                }
            }
        }


        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SmallGreyButton(
                text = stringResource(R.string.match_details_edit),
                onClick = { onEvent(MatchDetailsScreenContract.Event.EditClicked) },
                modifier = Modifier.weight(1f)
            )

            if (!isCompleted) {
                SmallGreyButton(
                    text = stringResource(R.string.match_details_attendance),
                    onClick = { onEvent(MatchDetailsScreenContract.Event.AttendanceClicked) },
                    modifier = Modifier.weight(1f)
                )
            }

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
                                onEvent(MatchDetailsScreenContract.Event.StatusChanged(status))
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

package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import java.time.format.DateTimeFormatter

@Composable
fun TrainingItemCard(
    training: Training,
    onDetailsClick: () -> Unit = {},
    onAttendanceClick: () -> Unit = {},
    onMarkAsClick: () -> Unit = {},
    onStatusChanged: (com.manager1700.soccer.domain.models.SportEventStatus) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showStatusDropdown by remember { mutableStateOf(false) }
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

    AppCard(
        title = training.type.uppercase(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
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

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    SportEventStatusChip(training.status)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Plan Attached",
                        fontSize = 10.sp,
                        color = colorGrey_89,
                        fontFamily = Montserrat
                    )
                }
            }


            // Attendance info
            Text(
                text = attendanceText,
                fontSize = 12.sp,
                color = colorGrey_89,
                fontFamily = Montserrat
            )

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SmallGreyButton(
                    text = stringResource(R.string.btn_details),
                    onClick = onDetailsClick,
                    modifier = Modifier.weight(1f)
                )

                if (!isCompleted) {
                    SmallGreyButton(
                        text = stringResource(R.string.btn_attendance),
                        onClick = onAttendanceClick,
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
                                    onStatusChanged(status)
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

@PreviewApp
@Composable
fun TrainingCardContentPreview() {
    SoccerManagerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBlack)
                .padding(all = 16.dp)
        ) {
            TrainingItemCard(
                training = Training.TEST_1
            )
        }
    }
}

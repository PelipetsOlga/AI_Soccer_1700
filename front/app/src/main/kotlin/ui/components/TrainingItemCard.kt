package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.domain.models.FutureAttendance
import com.manager1700.soccer.domain.models.PastAttendance
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorGreen
import com.manager1700.soccer.ui.theme.colorWhite
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TrainingItemCard(
    training: Training,
    onDetailsClick: () -> Unit = {},
    onAttendanceClick: () -> Unit = {},
    onMarkAsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isCompleted = training.status == SportEventStatus.Completed
    val statusColor = if (isCompleted) colorGreen else colorWhite
    val statusText = if (isCompleted) "COMPLETED" else "SCHEDULED"
    
    // Format date
    val formattedDate = training.date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
    val formattedTime = "${training.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${training.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
    
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorGrey_2b)
            .border(1.dp, colorGrey_89, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header row with title and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = training.type.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorWhite,
                    fontFamily = Montserrat,
                    modifier = Modifier.weight(1f)
                )
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(statusColor)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorBlack,
                            fontFamily = Montserrat
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Plan Attached",
                        fontSize = 10.sp,
                        color = colorGrey_89,
                        fontFamily = Montserrat
                    )
                }
            }
            
            // Training details
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
                ActionButton(
                    text = "DETAILS",
                    onClick = onDetailsClick,
                    modifier = Modifier.weight(1f)
                )
                
                if (!isCompleted) {
                    ActionButton(
                        text = "ATTENDANCE",
                        onClick = onAttendanceClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                ActionButton(
                    text = "MARK AS",
                    onClick = onMarkAsClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorGrey_2b)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = colorWhite,
            fontFamily = Montserrat,
            textAlign = TextAlign.Center
        )
    }
}

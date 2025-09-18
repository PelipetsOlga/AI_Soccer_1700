package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorRed
import com.manager1700.soccer.ui.theme.colorWhite
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TrainingCalendar(
    trainings: List<Training>,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onTrainingClick: (Training) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentMonth = selectedDate?.let { YearMonth.from(it) } ?: YearMonth.now()
    val today = LocalDate.now()
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorGrey_2b)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Month header with navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 20.sp,
                color = colorWhite,
                fontFamily = Montserrat,
                modifier = Modifier.clickable { 
                    // TODO: Navigate to previous month
                }
            )
            
            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorWhite,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "→",
                fontSize = 20.sp,
                color = colorWhite,
                fontFamily = Montserrat,
                modifier = Modifier.clickable { 
                    // TODO: Navigate to next month
                }
            )
        }
        
        // Days of week header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = colorGrey_89,
                    fontFamily = Montserrat,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(40.dp)
                )
            }
        }
        
        // Calendar grid
        val firstDayOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Convert to 0-6 where 0 is Monday
        val daysInMonth = currentMonth.lengthOfMonth()
        
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Generate calendar rows
            var currentDay = 1
            while (currentDay <= daysInMonth) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { column ->
                        if (currentDay == 1 && column < firstDayOfWeek) {
                            // Empty cell before first day of month
                            Spacer(modifier = Modifier.size(40.dp))
                        } else if (currentDay <= daysInMonth) {
                            val date = currentMonth.atDay(currentDay)
                            val hasTraining = trainings.any { it.date == date }
                            val isSelected = selectedDate == date
                            val isToday = date == today
                            
                            CalendarDay(
                                day = currentDay,
                                hasTraining = hasTraining,
                                isSelected = isSelected,
                                isToday = isToday,
                                onClick = { onDateSelected(date) },
                                modifier = Modifier.width(40.dp)
                            )
                            currentDay++
                        } else {
                            // Empty cell after last day of month
                            Spacer(modifier = Modifier.size(40.dp))
                        }
                    }
                }
            }
        }
        
        // Selected date trainings
        selectedDate?.let { date ->
            val dayTrainings = trainings.filter { it.date == date }
            if (dayTrainings.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "TRAININGS FOR ${date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")).uppercase()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorWhite,
                    fontFamily = Montserrat
                )
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dayTrainings) { training ->
                        TrainingItemCard(
                            training = training,
                            onDetailsClick = { onTrainingClick(training) },
                            onAttendanceClick = { onTrainingClick(training) },
                            onMarkAsClick = { onTrainingClick(training) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDay(
    day: Int,
    hasTraining: Boolean,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> colorRed
        isToday -> colorGrey_89
        else -> Color.Transparent
    }
    
    val textColor = when {
        isSelected -> colorWhite
        isToday -> colorBlack
        else -> colorWhite
    }
    
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            fontSize = 14.sp,
            fontWeight = if (hasTraining) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            fontFamily = Montserrat,
            textAlign = TextAlign.Center
        )
    }
}

package com.manager1700.soccer.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Exercise(
    val id: String,
    val title: String,
    val durationInMinutes: Int,
)

data class Training(
    val id: Int,
    override val status: SportEventStatus,
    override val date: LocalDate,
    override val startDateTime: LocalTime,
    override val endDateTime: LocalTime,
    override val type: String,
    override val note: String,
    override val place: String,
    override val title: String,
    val photos: List<String>,
    val exercises: List<Exercise>,
    override val plannedAttendance: AttendanceInfo,
    override val realAttendance: AttendanceInfo,
    ) : SportEvent
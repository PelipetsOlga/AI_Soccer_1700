package com.manager1700.soccer.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Exercise(
    val id: String,
    val title: String,
    val durationInMinutes: Int,
) {
    companion object {
        val EXERCISE_1 = Exercise(
            id = "1",
            title = "Push-ups",
            durationInMinutes = 5,
        )
        val EXERCISE_2 = Exercise(
            id = "2",
            title = "Run",
            durationInMinutes = 15,
        )
    }
}

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
) : SportEvent {
    companion object {
        val TEST_1 = Training(
            id = 1,
            status = SportEventStatus.Scheduled,
            date = LocalDate.now(),
            startDateTime = LocalTime.now(),
            endDateTime = LocalTime.now().plusHours(3),
            type = "Warm Up",
            note = "Blab bla bla, bla, Blab bla bla, bla, Blab bla bla, bla, Blab bla bla, bla, ",
            place = "Base 1",
            title = "First Training",
            photos = emptyList(),
            exercises = emptyList(),
            plannedAttendance = AttendanceInfo(info = emptyMap()),
            realAttendance = AttendanceInfo(info = emptyMap()),
        )
    }
}
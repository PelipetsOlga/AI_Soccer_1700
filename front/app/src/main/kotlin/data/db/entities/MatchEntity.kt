package com.manager1700.soccer.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.domain.models.LineupScheme
import com.manager1700.soccer.domain.models.SportEventStatus
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "matches")
@TypeConverters(TrainingConverters::class)
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val status: String, // Store as string key
    val date: LocalDate,
    val lineupScheme: String, // Store as string key
    val opponent: String,
    val startDateTime: LocalTime,
    val endDateTime: LocalTime,
    val type: String,
    val note: String,
    val place: String,
    val title: String,
    val plannedAttendance: AttendanceInfoEntity,
    val realAttendance: AttendanceInfoEntity,
)

// Extension functions to convert between Entity and Domain models
fun MatchEntity.toDomainModel(): Match {
    return Match(
        id = id,
        status = SportEventStatus.values().first { it.key == status },
        date = date,
        lineupScheme = LineupScheme.values().first { it.key == lineupScheme },
        opponent = opponent,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        type = type,
        note = note,
        place = place,
        title = title,
        plannedAttendance = plannedAttendance.toDomainModel(),
        realAttendance = realAttendance.toDomainModel(),
    )
}

fun Match.toEntity(): MatchEntity {
    return MatchEntity(
        id = id,
        status = status.key,
        date = date,
        lineupScheme = lineupScheme.key,
        opponent = opponent,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        type = type,
        note = note,
        place = place,
        title = title,
        plannedAttendance = plannedAttendance.toEntity(),
        realAttendance = realAttendance.toEntity(),
    )
}

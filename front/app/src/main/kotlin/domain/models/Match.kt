package com.application.domain.models

import com.application.domain.base.EnumWithKey
import java.time.LocalTime

enum class LineupScheme(
    override val key: String,
    val title: String,
) : EnumWithKey {
    Lineup_4_3_3(key = "Lineup_4_3_3", title = "4-3-3"),
    Lineup_4_4_2(key = "Lineup_4_4_2", title = "4-4-2"),
    Lineup_3_5_2(key = "Lineup_3_5_2", title = "3-5-2"),
}

data class Match(
    val id: Int,
    override val status: SportEventStatus,
    val lineupScheme: LineupScheme,
    val opponent: String,
    override val startDateTime: LocalTime,
    override val endDateTime: LocalTime,
    override val type: String,
    override val note: String,
    override val place: String,
    override val title: String,
    override val plannedAttendance: AttendanceInfo,
    override val realAttendance: AttendanceInfo,
) : SportEvent
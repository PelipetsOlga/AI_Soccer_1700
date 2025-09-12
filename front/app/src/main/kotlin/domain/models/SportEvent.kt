package com.manager1700.soccer.domain.models

import com.manager1700.soccer.R
import com.manager1700.soccer.domain.base.EnumWithKey
import java.time.LocalTime

interface SportEvent {
    val title: String
    val status: SportEventStatus
    val startDateTime: LocalTime
    val endDateTime: LocalTime
    val type: String
    val note: String
    val place: String
    val plannedAttendance: AttendanceInfo
    val realAttendance: AttendanceInfo
}

enum class SportEventStatus(
    override val key: String,
    val titleId: Int,
) : EnumWithKey {
    Scheduled(key = "Scheduled", titleId = R.string.match_status_scheduled),
    Completed(key = "Completed", titleId = R.string.match_status_completed),
    Canceled(key = "Canceled", titleId = R.string.match_status_canceled),
}
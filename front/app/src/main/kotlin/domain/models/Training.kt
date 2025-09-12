package com.application.domain.models

import java.time.LocalTime

data class Training(
    val id: Int,
    override val status: SportEventStatus,
    override val startDateTime: LocalTime,
    override val endDateTime: LocalTime,
    override val type: String,
    override val note: String,
    override val place: String,
    override val attendance: AttendanceInfo,

    ) : SportEvent
package com.application.domain.models

data class Relations (
    val id: Int,
    val sportEventId: Int,
    val playerId: Int,
    val futureAttendance: FutureAttendance?,
    val pastAttendance: PastAttendance?,
)
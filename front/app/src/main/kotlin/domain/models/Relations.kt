package com.manager1700.soccer.domain.models

data class Relations (
    val id: Int,
    val sportEventId: Int,
    val playerId: Int,
    val futureAttendance: FutureAttendance?,
    val pastAttendance: PastAttendance?,
)
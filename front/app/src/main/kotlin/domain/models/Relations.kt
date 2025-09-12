package com.application.domain.models

data class Relations (
    val sportEvent: SportEvent,
    val player: Player,
    val futureAttendance: FutureAttendance?,
    val pastAttendance: PastAttendance?,
)
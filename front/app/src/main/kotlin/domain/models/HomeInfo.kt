package com.manager1700.soccer.domain.models

data class TeamAlerts(
    val injured: Int,
    val lowFitness: Int,
    val lowAttendance: Int,
)

data class HomeInfo(
    val futureMatch: Match,
    val futureTraining: Training,
    val pastEvents: List<SportEvent>,
    val alerts: TeamAlerts,
)
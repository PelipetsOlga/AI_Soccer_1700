package com.manager1700.soccer.domain.repo

import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.Training
import java.time.LocalDate

interface SoccerRepository {

    suspend fun createPlayer(player: Player)
    suspend fun getPlayerById(id: Int): Player
    suspend fun deletePlayerById(id: Int)
    suspend fun updatePlayer(player: Player)
    suspend fun getAllPlayers(): List<Player>

    suspend fun createTraining(training: Training)
    suspend fun updateTraining(training: Training)
    suspend fun deleteTraining(id: Int)
    suspend fun getTrainingById(id: Int): Training
    suspend fun getAllTrainings(): List<Training>
    suspend fun getFutureTrainings(): List<Training>
    suspend fun getPastTrainings(): List<Training>
    suspend fun getTrainingsForDay(date: LocalDate): List<Training>
    suspend fun getTrainingDaysForMonth(date: LocalDate): List<Training>

    suspend fun createMatch(match: Match)
    suspend fun updateMatch(match: Match)
    suspend fun deleteMatch(id: Int)
    suspend fun getMatchById(id: Int): Match
    suspend fun getAllMatches(): List<Match>
    suspend fun getFutureMatchs(): List<Match>
    suspend fun getPastMatchs(): List<Match>
    suspend fun getMatchsForDay(date: LocalDate): List<Match>
    suspend fun getMatchDaysForMonth(date: LocalDate): List<Match>
}
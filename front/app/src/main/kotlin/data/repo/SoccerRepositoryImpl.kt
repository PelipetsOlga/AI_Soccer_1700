package com.application.data.repo

import com.application.domain.models.Match
import com.application.domain.models.Player
import com.application.domain.models.Training
import com.application.domain.repo.SoccerRepository
import java.time.LocalDate

class SoccerRepositoryImpl: SoccerRepository {
    override suspend fun createPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerById(id: Int): Player {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlayerById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPlayers(): List<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun createTraining(training: Training) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTraining(training: Training) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTraining(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingById(id: Int): Training {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTrainings(): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun getFutureTrainings(): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun getPastTrainings(): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingsForDay(date: LocalDate): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingDaysForMonth(date: LocalDate): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun createMatch(match: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMatch(match: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMatch(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchById(id: Int): Match {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMatches(): List<Match> {
        TODO("Not yet implemented")
    }

    override suspend fun getFutureMatchs(): List<Match> {
        TODO("Not yet implemented")
    }

    override suspend fun getPastMatchs(): List<Match> {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchsForDay(date: LocalDate): List<Match> {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchDaysForMonth(date: LocalDate): List<Match> {
        TODO("Not yet implemented")
    }
}
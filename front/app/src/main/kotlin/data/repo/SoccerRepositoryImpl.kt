package com.manager1700.soccer.data.repo

import com.manager1700.soccer.data.db.dao.MatchDao
import com.manager1700.soccer.data.db.dao.PlayerDao
import com.manager1700.soccer.data.db.dao.TrainingDao
import com.manager1700.soccer.data.db.entities.toDomainModel
import com.manager1700.soccer.data.db.entities.toEntity
import com.manager1700.soccer.domain.models.Match
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.domain.models.Training
import com.manager1700.soccer.domain.repo.SoccerRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalTime

class SoccerRepositoryImpl(
    private val playerDao: PlayerDao,
    private val trainingDao: TrainingDao,
    private val matchDao: MatchDao
) : SoccerRepository {
    // Player methods
    override suspend fun createPlayer(player: Player) {
        playerDao.insertPlayer(player.toEntity())
    }

    override suspend fun getPlayerById(id: Int): Player {
        return playerDao.getPlayerById(id)?.toDomainModel()
            ?: throw NoSuchElementException("Player with id $id not found")
    }

    override suspend fun deletePlayerById(id: Int) {
        playerDao.deletePlayerById(id)
    }

    override suspend fun updatePlayer(player: Player) {
        playerDao.updatePlayer(player.toEntity())
    }

    override suspend fun getAllPlayers(): List<Player> {
        return playerDao.getAllPlayers().first().map { it.toDomainModel() }
    }

    // Training methods
    override suspend fun createTraining(training: Training) {
        trainingDao.insertTraining(training.toEntity())
    }

    override suspend fun updateTraining(training: Training) {
        trainingDao.updateTraining(training.toEntity())
    }

    override suspend fun deleteTraining(id: Int) {
        trainingDao.deleteTrainingById(id)
    }

    override suspend fun getTrainingById(id: Int): Training {
        return trainingDao.getTrainingById(id)?.toDomainModel()
            ?: throw NoSuchElementException("Training with id $id not found")
    }

    override suspend fun getAllTrainings(): List<Training> {
        return trainingDao.getAllTrainings().first().map { it.toDomainModel() }
    }

    override suspend fun getFutureTrainings(): List<Training> {
        val allTrainings = trainingDao.getAllTrainings().first()
        val currentTime = LocalTime.now()
        return allTrainings
            .filter { entity ->
                entity.status == SportEventStatus.Scheduled.key &&
                        entity.startDateTime.isAfter(currentTime)
            }
            .map { it.toDomainModel() }
    }

    override suspend fun getPastTrainings(): List<Training> {
        val allTrainings = trainingDao.getAllTrainings().first()
        val currentTime = LocalTime.now()
        return allTrainings
            .filter { entity ->
                entity.status == SportEventStatus.Completed.key ||
                        (entity.status == SportEventStatus.Scheduled.key && entity.endDateTime.isBefore(
                            currentTime
                        ))
            }
            .map { it.toDomainModel() }
    }

    override suspend fun getTrainingsForDay(date: LocalDate): List<Training> {
        // Note: Since Training uses LocalTime instead of LocalDateTime,
        // we can't filter by date directly. This implementation returns all trainings.
        // In a real app, you might want to add a date field to Training entity.
        return getAllTrainings()
    }

    override suspend fun getTrainingDaysForMonth(date: LocalDate): List<Training> {
        // Similar to getTrainingsForDay, this would need a date field in the entity
        // to properly filter by month. For now, returning all trainings.
        return getAllTrainings()
    }

    // Match methods
    override suspend fun createMatch(match: Match) {
        matchDao.insertMatch(match.toEntity())
    }

    override suspend fun updateMatch(match: Match) {
        matchDao.updateMatch(match.toEntity())
    }

    override suspend fun deleteMatch(id: Int) {
        matchDao.deleteMatchById(id)
    }

    override suspend fun getMatchById(id: Int): Match {
        return matchDao.getMatchById(id)?.toDomainModel()
            ?: throw NoSuchElementException("Match with id $id not found")
    }

    override suspend fun getAllMatches(): List<Match> {
        return matchDao.getAllMatches().first().map { it.toDomainModel() }
    }

    override suspend fun getFutureMatchs(): List<Match> {
        val allMatches = matchDao.getAllMatches().first()
        val currentTime = LocalTime.now()
        return allMatches
            .filter { entity ->
                entity.status == SportEventStatus.Scheduled.key &&
                        entity.startDateTime.isAfter(currentTime)
            }
            .map { it.toDomainModel() }
    }

    override suspend fun getPastMatchs(): List<Match> {
        val allMatches = matchDao.getAllMatches().first()
        val currentTime = LocalTime.now()
        return allMatches
            .filter { entity ->
                entity.status == SportEventStatus.Completed.key ||
                        (entity.status == SportEventStatus.Scheduled.key && entity.endDateTime.isBefore(
                            currentTime
                        ))
            }
            .map { it.toDomainModel() }
    }

    override suspend fun getMatchsForDay(date: LocalDate): List<Match> {
        // Note: Since Match uses LocalTime instead of LocalDateTime,
        // we can't filter by date directly. This implementation returns all matches.
        // In a real app, you might want to add a date field to Match entity.
        return getAllMatches()
    }

    override suspend fun getMatchDaysForMonth(date: LocalDate): List<Match> {
        // Similar to getMatchsForDay, this would need a date field in the entity
        // to properly filter by month. For now, returning all matches.
        return getAllMatches()
    }
}
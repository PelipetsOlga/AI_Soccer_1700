package com.manager1700.soccer.data.db.dao

import androidx.room.*
import com.manager1700.soccer.data.db.entities.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    
    @Query("SELECT * FROM trainings ORDER BY startDateTime ASC")
    fun getAllTrainings(): Flow<List<TrainingEntity>>
    
    @Query("SELECT * FROM trainings WHERE id = :trainingId")
    suspend fun getTrainingById(trainingId: Int): TrainingEntity?
    
    @Query("SELECT * FROM trainings WHERE status = :status ORDER BY startDateTime ASC")
    fun getTrainingsByStatus(status: String): Flow<List<TrainingEntity>>
    
    @Query("SELECT * FROM trainings WHERE type = :type ORDER BY startDateTime ASC")
    fun getTrainingsByType(type: String): Flow<List<TrainingEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(training: TrainingEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainings(trainings: List<TrainingEntity>)
    
    @Update
    suspend fun updateTraining(training: TrainingEntity)
    
    @Delete
    suspend fun deleteTraining(training: TrainingEntity)
    
    @Query("DELETE FROM trainings WHERE id = :trainingId")
    suspend fun deleteTrainingById(trainingId: Int)
    
    @Query("DELETE FROM trainings")
    suspend fun deleteAllTrainings()
}

package com.application.data.db.dao

import androidx.room.*
import com.application.data.db.entities.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    
    @Query("SELECT * FROM matches ORDER BY startDateTime ASC")
    fun getAllMatches(): Flow<List<MatchEntity>>
    
    @Query("SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): MatchEntity?
    
    @Query("SELECT * FROM matches WHERE status = :status ORDER BY startDateTime ASC")
    fun getMatchesByStatus(status: String): Flow<List<MatchEntity>>
    
    @Query("SELECT * FROM matches WHERE opponent = :opponent ORDER BY startDateTime ASC")
    fun getMatchesByOpponent(opponent: String): Flow<List<MatchEntity>>
    
    @Query("SELECT * FROM matches WHERE lineupScheme = :scheme ORDER BY startDateTime ASC")
    fun getMatchesByLineupScheme(scheme: String): Flow<List<MatchEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)
    
    @Update
    suspend fun updateMatch(match: MatchEntity)
    
    @Delete
    suspend fun deleteMatch(match: MatchEntity)
    
    @Query("DELETE FROM matches WHERE id = :matchId")
    suspend fun deleteMatchById(matchId: Int)
    
    @Query("DELETE FROM matches")
    suspend fun deleteAllMatches()
}

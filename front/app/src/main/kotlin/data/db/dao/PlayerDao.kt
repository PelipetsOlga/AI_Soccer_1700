package com.manager1700.soccer.data.db.dao

import androidx.room.*
import com.manager1700.soccer.data.db.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    
    @Query("SELECT * FROM players ORDER BY number ASC")
    fun getAllPlayers(): Flow<List<PlayerEntity>>
    
    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerById(playerId: Int): PlayerEntity?
    
    @Query("SELECT * FROM players WHERE number = :number")
    suspend fun getPlayerByNumber(number: Int): PlayerEntity?
    
    @Query("SELECT * FROM players WHERE status = :status")
    fun getPlayersByStatus(status: String): Flow<List<PlayerEntity>>
    
    @Query("SELECT * FROM players WHERE position = :position")
    fun getPlayersByPosition(position: String): Flow<List<PlayerEntity>>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: PlayerEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<PlayerEntity>)
    
    @Update
    suspend fun updatePlayer(player: PlayerEntity)
    
    @Delete
    suspend fun deletePlayer(player: PlayerEntity)
    
    @Query("DELETE FROM players WHERE id = :playerId")
    suspend fun deletePlayerById(playerId: Int)
    
    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()
}

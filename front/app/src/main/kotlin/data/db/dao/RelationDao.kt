package com.application.data.db.dao

import androidx.room.*
import com.application.data.db.entities.RelationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RelationDao {
    
    @Query("SELECT * FROM relations")
    fun getAllRelations(): Flow<List<RelationEntity>>
    
    @Query("SELECT * FROM relations WHERE id = :relationId")
    suspend fun getRelationById(relationId: Int): RelationEntity?
    
    @Query("SELECT * FROM relations WHERE playerId = :playerId")
    fun getRelationsByPlayerId(playerId: Int): Flow<List<RelationEntity>>
    
    @Query("SELECT * FROM relations WHERE sportEventId = :sportEventId")
    fun getRelationsBySportEventId(sportEventId: Int): Flow<List<RelationEntity>>
    
    @Query("SELECT * FROM relations WHERE playerId = :playerId AND sportEventId = :sportEventId")
    suspend fun getRelationByPlayerAndEvent(playerId: Int, sportEventId: Int): RelationEntity?
    
    @Query("SELECT * FROM relations WHERE futureAttendance = :attendance")
    fun getRelationsByFutureAttendance(attendance: String): Flow<List<RelationEntity>>
    
    @Query("SELECT * FROM relations WHERE pastAttendance = :attendance")
    fun getRelationsByPastAttendance(attendance: String): Flow<List<RelationEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(relation: RelationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelations(relations: List<RelationEntity>)
    
    @Update
    suspend fun updateRelation(relation: RelationEntity)
    
    @Delete
    suspend fun deleteRelation(relation: RelationEntity)
    
    @Query("DELETE FROM relations WHERE id = :relationId")
    suspend fun deleteRelationById(relationId: Int)
    
    @Query("DELETE FROM relations WHERE playerId = :playerId")
    suspend fun deleteRelationsByPlayerId(playerId: Int)
    
    @Query("DELETE FROM relations WHERE sportEventId = :sportEventId")
    suspend fun deleteRelationsBySportEventId(sportEventId: Int)
    
    @Query("DELETE FROM relations")
    suspend fun deleteAllRelations()
}

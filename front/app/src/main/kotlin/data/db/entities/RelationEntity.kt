package com.application.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import com.application.domain.models.Relations
import com.application.domain.models.FutureAttendance
import com.application.domain.models.PastAttendance

@Entity(
    tableName = "relations",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["playerId"]),
        Index(value = ["sportEventId"]),
        Index(value = ["sportEventId", "playerId"], unique = true)
    ]
)
data class RelationEntity(
    @PrimaryKey
    val id: Int,
    val sportEventId: Int,
    val playerId: Int,
    val futureAttendance: String?, // Store as string key, nullable
    val pastAttendance: String?, // Store as string key, nullable
)

// Extension functions to convert between Entity and Domain models
fun RelationEntity.toDomainModel(): Relations {
    return Relations(
        id = id,
        sportEventId = sportEventId,
        playerId = playerId,
        futureAttendance = futureAttendance?.let { key ->
            FutureAttendance.values().find { it.key == key }
        },
        pastAttendance = pastAttendance?.let { key ->
            PastAttendance.values().find { it.key == key }
        },
    )
}

fun Relations.toEntity(): RelationEntity {
    return RelationEntity(
        id = id,
        sportEventId = sportEventId,
        playerId = playerId,
        futureAttendance = futureAttendance?.key,
        pastAttendance = pastAttendance?.key,
    )
}

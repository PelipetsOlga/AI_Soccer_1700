package com.application.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.domain.models.Player
import com.application.domain.models.Foot
import com.application.domain.models.Position
import com.application.domain.models.PlayerStatus

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    val id: Int,
    val number: Int,
    val position: String, // Store as string key
    val foot: String, // Store as string key
    val attendance: Int,
    val sessions: Int,
    val fitness: Int,
    val status: String, // Store as string key
    val note: String,
    val dateOfInjury: String?,
    val noteOfInjury: String?,
    val imageUrl: String?,
)

// Extension functions to convert between Entity and Domain models
fun PlayerEntity.toDomainModel(): Player {
    return Player(
        id = id,
        number = number,
        position = Position.values().first { it.key == position },
        foot = Foot.values().first { it.key == foot },
        attendance = attendance,
        sessions = sessions,
        fitness = fitness,
        status = PlayerStatus.values().first { it.key == status },
        note = note,
        dateOfInjury = dateOfInjury,
        noteOfInjury = noteOfInjury,
        imageUrl = imageUrl,
    )
}

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        id = id,
        number = number,
        position = position.key,
        foot = foot.key,
        attendance = attendance,
        sessions = sessions,
        fitness = fitness,
        status = status.key,
        note = note,
        dateOfInjury = dateOfInjury,
        noteOfInjury = noteOfInjury,
        imageUrl = imageUrl,
    )
}

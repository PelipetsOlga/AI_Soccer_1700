package com.manager1700.soccer.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.domain.models.Position
import com.manager1700.soccer.domain.models.PlayerStatus

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val number: Int,
    val position: String, // Store as string key
    val foot: String, // Store as string key
    val fitness: Int,
    val status: String, // Store as string key
    val note: String,
    val dateOfInjury: String?,
    val imageUrl: String?,
)

// Extension functions to convert between Entity and Domain models
fun PlayerEntity.toDomainModel(): Player {
    return Player(
        id = id,
        name = name,
        number = number,
        position = Position.entries.first { it.key == position },
        foot = Foot.entries.first { it.key == foot },
        fitness = fitness,
        status = PlayerStatus.entries.first { it.key == status },
        note = note,
        dateOfInjury = dateOfInjury,
        imageUrl = imageUrl,
    )
}

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        id = id,
        name = name,
        number = number,
        position = position.key,
        foot = foot.key,
        fitness = fitness,
        status = status.key,
        note = note,
        dateOfInjury = dateOfInjury,
        imageUrl = imageUrl,
    )
}

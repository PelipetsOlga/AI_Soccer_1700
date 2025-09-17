package com.manager1700.soccer.domain.models

import com.manager1700.soccer.R
import com.manager1700.soccer.domain.base.EnumWithKey


enum class Foot(
    override val key: String,
    val titleId: Int,
) : EnumWithKey {
    Right(key = "Right", titleId = R.string.foot_right),
    Left(key = "Left", titleId = R.string.foot_left),
}

enum class Position(
    override val key: String,
    val shortName: String,
    val fullNameId: Int,
) : EnumWithKey {
    Defender(key = "df", shortName = "DF", fullNameId = R.string.role_defender),
    Midfielder(key = "md", shortName = "MD", fullNameId = R.string.role_midfielderr),
    Goalkeeper(key = "gk", shortName = "GK", fullNameId = R.string.role_goalkeeper),
    Forward(key = "fw", shortName = "FW", fullNameId = R.string.role_forward),
}

enum class PlayerStatus(
    override val key: String,
    val titleId: Int,
) : EnumWithKey {
    Active(key = "active", titleId = R.string.player_status_active),
    Injured(key = "injured", titleId = R.string.player_status_injured),
}

data class Player(
    val id: Int,
    val name: String,
    val number: Int,
    val position: Position,
    val foot: Foot,
    val fitness: Int,
    val status: PlayerStatus,
    val note: String,
    val dateOfInjury: String?,
    val imageUrl: String?,
) {
    companion object {
        val EMPTY = Player(
            id = 0,
            name = "",
            number = -1,
            position = Position.Defender,
            foot = Foot.Right,
            fitness = 100,
            status = PlayerStatus.Active,
            note = "",
            dateOfInjury = null,
            imageUrl = null,
        )

        val TEST_1 = Player(
            name = "Martin Gomes",
            number = 21,
            id = 11,
            position = Position.Defender,
            fitness = 99,
            status = PlayerStatus.Active,
            foot = Foot.Right,
            note = "He is a new comer from New York. 19 Years old. 75 kg.",
            dateOfInjury = null,
            imageUrl = null,
        )
    }
}
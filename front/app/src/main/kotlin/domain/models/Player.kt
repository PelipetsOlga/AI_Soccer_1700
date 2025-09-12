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
    val number: Int,
    val position: Position,
    val foot: Foot,
    val attendance: Int,
    val sessions: Int,
    val fitness: Int,
    val status: PlayerStatus,
    val note: String,
    val dateOfInjury: String?,
    val noteOfInjury: String?,
    val imageUrl: String?,
) {
    companion object {
        val EMPTY = Player(
            id = -1,
            number = -1,
            position = Position.Defender,
            foot = Foot.Right,
            attendance = 0,
            sessions = 0,
            fitness = 100,
            status = PlayerStatus.Active,
            note = "",
            dateOfInjury = null,
            noteOfInjury = null,
            imageUrl = null,
        )
    }
}
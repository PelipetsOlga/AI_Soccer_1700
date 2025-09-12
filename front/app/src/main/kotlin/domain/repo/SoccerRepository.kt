package com.manager1700.soccer.domain.repo

import com.manager1700.soccer.domain.models.Player

interface SoccerRepository {

    suspend fun getPlayerById(id: Int): Player
    suspend fun deletePlayerById(id: Int)
    suspend fun updatePlayer(player: Player)
}
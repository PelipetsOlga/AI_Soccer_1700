package com.application.domain.repo

import com.application.domain.models.Player

interface SoccerRepository {

    suspend fun getPlayerById(id: Int): Player
    suspend fun deletePlayerById(id: Int)
    suspend fun updatePlayer(player: Player)
}
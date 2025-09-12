package com.manager1700.soccer.data.repo

import android.content.Context
import com.manager1700.soccer.data.db.SportManagerDatabase
import com.manager1700.soccer.domain.repo.SoccerRepository

/**
 * Factory class for creating repository instances with proper database dependencies
 */
object RepositoryFactory {
    
    fun createSoccerRepository(context: Context): SoccerRepository {
        val database = SportManagerDatabase.getDatabase(context)
        return SoccerRepositoryImpl(
            playerDao = database.playerDao(),
            trainingDao = database.trainingDao(),
            matchDao = database.matchDao()
        )
    }
}

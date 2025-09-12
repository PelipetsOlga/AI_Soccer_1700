package com.application.data.repo

import android.content.Context
import com.application.data.db.SportManagerDatabase
import com.application.domain.repo.SoccerRepository

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

package com.application.data.db

import android.content.Context
import com.application.data.repo.SoccerRepositoryImpl
import com.application.domain.repo.SoccerRepository

/**
 * Database module for providing database instance, DAOs, and repositories
 * This can be used with dependency injection frameworks like Dagger/Hilt
 */
object DatabaseModule {
    
    @Volatile
    private var database: SportManagerDatabase? = null
    
    @Volatile
    private var soccerRepository: SoccerRepository? = null
    
    fun provideDatabase(context: Context): SportManagerDatabase {
        return database ?: synchronized(this) {
            val instance = SportManagerDatabase.getDatabase(context)
            database = instance
            instance
        }
    }
    
    fun providePlayerDao(context: Context) = provideDatabase(context).playerDao()
    
    fun provideTrainingDao(context: Context) = provideDatabase(context).trainingDao()
    
    fun provideMatchDao(context: Context) = provideDatabase(context).matchDao()
    
    fun provideRelationDao(context: Context) = provideDatabase(context).relationDao()
    
    fun provideSoccerRepository(context: Context): SoccerRepository {
        return soccerRepository ?: synchronized(this) {
            val db = provideDatabase(context)
            val instance = SoccerRepositoryImpl(
                playerDao = db.playerDao(),
                trainingDao = db.trainingDao(),
                matchDao = db.matchDao()
            )
            soccerRepository = instance
            instance
        }
    }
}

package com.application.data.db

import android.content.Context

/**
 * Database module for providing database instance and DAOs
 * This can be used with dependency injection frameworks like Dagger/Hilt
 */
object DatabaseModule {
    
    @Volatile
    private var database: SportManagerDatabase? = null
    
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
}

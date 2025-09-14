package com.manager1700.soccer.data.di

import android.content.Context
import androidx.room.Room
import com.manager1700.soccer.data.db.SportManagerDatabase
import com.manager1700.soccer.data.db.dao.MatchDao
import com.manager1700.soccer.data.db.dao.PlayerDao
import com.manager1700.soccer.data.db.dao.TrainingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SportManagerDatabase {
        return Room.databaseBuilder(
            context,
            SportManagerDatabase::class.java,
            "sport_manager_database"
        ).build()
    }

    @Provides
    fun providePlayerDao(database: SportManagerDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    fun provideTrainingDao(database: SportManagerDatabase): TrainingDao {
        return database.trainingDao()
    }

    @Provides
    fun provideMatchDao(database: SportManagerDatabase): MatchDao {
        return database.matchDao()
    }
}

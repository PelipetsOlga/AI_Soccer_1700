package com.manager1700.soccer.data.di

import com.manager1700.soccer.data.db.dao.MatchDao
import com.manager1700.soccer.data.db.dao.PlayerDao
import com.manager1700.soccer.data.db.dao.TrainingDao
import com.manager1700.soccer.data.repo.SoccerRepositoryImpl
import com.manager1700.soccer.domain.repo.SoccerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        playerDao: PlayerDao,
        trainingDao: TrainingDao,
        matchDao: MatchDao
    ): SoccerRepository {
        return SoccerRepositoryImpl(
            playerDao = playerDao,
            trainingDao = trainingDao,
            matchDao = matchDao
        )
    }
}
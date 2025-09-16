package com.manager1700.soccer.data.di

import android.content.Context
import com.manager1700.soccer.data.utils.ImageFileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    @Singleton
    fun provideImageFileManager(@ApplicationContext context: Context): ImageFileManager {
        return ImageFileManager(context)
    }
}


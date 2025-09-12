package com.application.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.application.data.db.entities.PlayerEntity
import com.application.data.db.entities.TrainingEntity
import com.application.data.db.entities.MatchEntity
import com.application.data.db.entities.RelationEntity
import com.application.data.db.entities.TrainingConverters
import com.application.data.db.dao.PlayerDao
import com.application.data.db.dao.TrainingDao
import com.application.data.db.dao.MatchDao
import com.application.data.db.dao.RelationDao

@Database(
    entities = [
        PlayerEntity::class,
        TrainingEntity::class,
        MatchEntity::class,
        RelationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TrainingConverters::class)
abstract class SportManagerDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun trainingDao(): TrainingDao
    abstract fun matchDao(): MatchDao
    abstract fun relationDao(): RelationDao

    companion object {
        @Volatile
        private var INSTANCE: SportManagerDatabase? = null

        fun getDatabase(context: Context): SportManagerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SportManagerDatabase::class.java,
                    "sport_manager_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

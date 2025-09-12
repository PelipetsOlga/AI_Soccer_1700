package com.manager1700.soccer.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.manager1700.soccer.data.db.entities.PlayerEntity
import com.manager1700.soccer.data.db.entities.TrainingEntity
import com.manager1700.soccer.data.db.entities.MatchEntity
import com.manager1700.soccer.data.db.entities.RelationEntity
import com.manager1700.soccer.data.db.entities.TrainingConverters
import com.manager1700.soccer.data.db.dao.PlayerDao
import com.manager1700.soccer.data.db.dao.TrainingDao
import com.manager1700.soccer.data.db.dao.MatchDao
import com.manager1700.soccer.data.db.dao.RelationDao

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

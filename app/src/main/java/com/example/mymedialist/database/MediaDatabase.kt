package com.example.mymedialist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 4)
abstract class MediaDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MediaDatabase? = null

        fun getInstance(context: Context): MediaDatabase {
            synchronized(MediaDatabase::class.java) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MediaDatabase::class.java,
                        "media_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}







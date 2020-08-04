package com.example.mymedialist.database

import android.content.Context
import androidx.room.*
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.util.DateConverter


@Database(entities = [MovieEntity::class], version = 7)
@TypeConverters(DateConverter::class)
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







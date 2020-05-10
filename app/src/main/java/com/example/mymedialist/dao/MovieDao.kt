package com.example.mymedialist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymedialist.model.MovieEntity

@Dao
interface MovieDao {

    @Query("select * from movies")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)
}

package com.example.mymedialist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymedialist.model.MovieEntity
import java.time.LocalDate

@Dao
interface MovieDao {

    @Query("select * from movies order by date(seen_on_date) desc")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Query("select * from movies where title like :title and release_year like :releaseYear")
    fun searchForEntity(title: String, releaseYear: String): MovieEntity?

    @Query("select * from movies where date(seen_on_date) between date(:from) and date(:to)")
    fun findMoviesWatchedBetween(from: LocalDate, to: LocalDate): List<MovieEntity>
}

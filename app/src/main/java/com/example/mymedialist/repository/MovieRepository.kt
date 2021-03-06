package com.example.mymedialist.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.util.DateConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate

// TODO (perfect for DI or to turn into singleton)

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun insertEntity(entity: MovieEntity) {
        withContext(Dispatchers.IO) {
            var releaseYear = if(!entity.releaseYear.isNullOrEmpty()) {
                entity.releaseYear
            } else {
                ""
            }
            var movie = movieDao.searchForEntity(entity.title, releaseYear)
            Timber.i("Values: ${entity.title} $releaseYear")
            Timber.i("Movie searched: $movie")
            if (movie != null) {
                Timber.i("Movie found!")
                movie.seenOnDate = entity.seenOnDate
                movie.rating = entity.rating
                movieDao.insertAll(movie)
            } else movieDao.insertAll(entity)

        }
    }

    suspend fun deleteEntity(entity: MovieEntity) {
        withContext((Dispatchers.IO)) {
            movieDao.delete(entity)
        }
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    fun searchForMovie(title: String, releaseYear: String): MovieEntity? {
        return movieDao.searchForEntity(title, releaseYear)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMoviesWatchedInPeriod(from: LocalDate, to:LocalDate): List<MovieEntity> {
        Timber.i("Inside MovieRepository! From: ${DateConverter.fromDate(from)}, To: ${DateConverter.fromDate(to)}")
        return movieDao.findMoviesWatchedBetween(from, to)
    }

}
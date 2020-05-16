package com.example.mymedialist.repository

import androidx.lifecycle.LiveData
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// TODO (perfect for DI or to turn into singleton)

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun insertEntity(entity: MovieEntity) {
        withContext(Dispatchers.IO) {
            movieDao.insertAll(entity)
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

}
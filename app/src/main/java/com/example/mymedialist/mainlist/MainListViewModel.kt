package com.example.mymedialist.mainlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.model.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class MainListViewModel(
    val database: MovieDao,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val movies = database.getMovies()

   private suspend fun insertEntity(entity: MovieEntity) {
       withContext(Dispatchers.IO) {
           database.insertAll(entity)
       }
   }

    private suspend fun deleteEntity(entity: MovieEntity) {
        withContext((Dispatchers.IO)) {
            database.delete(entity)
        }
    }
}


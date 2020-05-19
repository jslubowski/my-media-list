package com.example.mymedialist.mainlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainListViewModel(
    private val datasource: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToAddScreen = MutableLiveData<Boolean?>()
    val navigateToAddScreen: LiveData<Boolean?>
        get() = _navigateToAddScreen

    private val _navigateToMovieDetails = MutableLiveData<MovieEntity>()
    val navigateToMovieDetails: LiveData<MovieEntity>
        get() = _navigateToMovieDetails

    val movies = datasource.getAllMovies()

    fun onAddButtonClick() {
        _navigateToAddScreen.value = true
    }

    fun displayMovieDetails(movieEntity: MovieEntity) {
        _navigateToMovieDetails.value = movieEntity
//        return true
    }

    fun displayMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating() {
        _navigateToAddScreen.value = null
    }

    fun removeFromDatabase(movieEntity: MovieEntity) {
        uiScope.launch {
            datasource.deleteEntity(movieEntity)
        }
    }

}


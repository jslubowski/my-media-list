package com.example.mymedialist.addmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.network.TmdbApi
import com.example.mymedialist.repository.MovieRepository
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.Result as Result

class AddMediaViewModel(
    val database: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private val API_KEY         = "cd0a70c0c0a80b3301a97accc396daa5"
    private val LANGUAGE        = "en-US"
    private val PAGE            = "1"
    private val INCLUDE_ADULT   = "false"
    private val MAX_SIZE        = 3

    private var addMediaJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + addMediaJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + addMediaJob)

    private val _navigateToList = MutableLiveData<Boolean?>()
    val navigateToList: LiveData<Boolean?>
        get() = _navigateToList

    private val _searchMovies = MutableLiveData<Boolean?>()
    val searchMovies: LiveData<Boolean?>
        get() = _searchMovies

    private val _moviesList = MutableLiveData<List<com.example.mymedialist.network.Result>>()
    val moviesList: LiveData<List<com.example.mymedialist.network.Result>>
        get() = _moviesList

    init {
        _moviesList.value = ArrayList()
    }

    fun onSearchButtonClicked() {
        _searchMovies.value = true
    }

    fun addMovieToDatabase(movieEntity: MovieEntity) {
        ioScope.launch {
            database.insertEntity(movieEntity)
        }
    }

    fun doneNavigating() {
        _navigateToList.value = null
    }

    fun doneSearching() {
        _searchMovies.value = null
    }

    fun searchForMovies(title: String) {
        ioScope.launch {
            _moviesList.postValue(TmdbApi.retrofitService.searchMovie(
                API_KEY,
                LANGUAGE,
                title,
                PAGE,
                INCLUDE_ADULT
            ).results)
        }
    }

    override fun onCleared() {
        super.onCleared()
        addMediaJob.cancel()
    }
}
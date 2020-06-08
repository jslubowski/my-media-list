package com.example.mymedialist.addmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.network.TmdbApi
import com.example.mymedialist.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AddMediaViewModel(
    val database: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private val API_KEY = "cd0a70c0c0a80b3301a97accc396daa5"
    private val LANGUAGE = "en-US"
    private val PAGE = "1"
    private val INCLUDE_ADULT = "false"

    private var addMediaJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + addMediaJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + addMediaJob)

    private val _navigateToList = MutableLiveData<Boolean?>()
    val navigateToList: LiveData<Boolean?>
        get() = _navigateToList

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    fun onAddButtonClick() {
        _navigateToList.value = true
    }

    fun addMovieToDatabase(movieEntity: MovieEntity) {
        ioScope.launch {
            database.insertEntity(movieEntity)
        }
    }

    fun doneNavigating() {
        _navigateToList.value = null
    }

    fun getMovieSearchResult(title: String) {
        ioScope.launch {
            val responseBody =
                TmdbApi.retrofitService.searchMovie(API_KEY, LANGUAGE, title, PAGE, INCLUDE_ADULT)
            Timber.i("API: ${responseBody.results}")
        }
    }
}
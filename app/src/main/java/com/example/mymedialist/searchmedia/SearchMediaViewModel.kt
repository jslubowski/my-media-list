package com.example.mymedialist.searchmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.network.TmdbApi
import com.example.mymedialist.repository.MovieRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class SearchMediaViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val API_KEY = "cd0a70c0c0a80b3301a97accc396daa5"
    private val LANGUAGE = "en-US"
    private val PAGE = "1"
    private val INCLUDE_ADULT = "false"

    private var addMediaJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + addMediaJob)

    private val _searchMovies = MutableLiveData<Boolean?>()
    val searchMovies: LiveData<Boolean?>
        get() = _searchMovies

    private val _moviesList = MutableLiveData<List<com.example.mymedialist.network.Result>>()
    val moviesList: LiveData<List<com.example.mymedialist.network.Result>>
        get() = _moviesList

    private val _status = MutableLiveData<TmdbApiStatus>()
    val status: LiveData<TmdbApiStatus>
        get() = _status

    init {
        _moviesList.value = ArrayList()
    }

    fun onSearchButtonClicked() {
        _searchMovies.value = true
    }

    fun doneSearching() {
        _searchMovies.value = null
    }

    fun searchForMovies(title: String) {
        try {
            ioScope.launch {
                _moviesList.postValue(
                    TmdbApi.retrofitService.searchMovie(
                        API_KEY,
                        LANGUAGE,
                        title,
                        PAGE,
                        INCLUDE_ADULT
                    ).results
                )
            }
        } catch (e: Exception) {
            _status.value = TmdbApiStatus.ERROR
            _moviesList.value = ArrayList()
        }
    }

    fun changeLoadingStatus(status: TmdbApiStatus) {
        _status.value = status
        Timber.i("Status was changed to ${_status.value}")
    }

    override fun onCleared() {
        super.onCleared()
        addMediaJob.cancel()
    }

    enum class TmdbApiStatus { LOADING, ERROR, DONE }
}
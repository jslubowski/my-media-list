package com.example.mymedialist.details

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

class MovieDetailsViewModel(
    application: Application,
    movieEntity: MovieEntity,
    private val database: MovieRepository
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedMovie = MutableLiveData<MovieEntity>()
    val selectedMovie: LiveData<MovieEntity>
        get() = _selectedMovie

    private val _EditAndNavigateToMainList = MutableLiveData<Boolean?>()
    val EditAndNavigateToMainList: LiveData<Boolean?>
        get() = _EditAndNavigateToMainList

    init {
        _selectedMovie.value = movieEntity
    }

    fun onEditButtonClick() {
        _EditAndNavigateToMainList.value = true
    }

    fun deleteCurrentEntity() {
        uiScope.launch {
            database.deleteEntity(_selectedMovie.value!!)
        }
    }

    fun doneNavigating(){
        _EditAndNavigateToMainList.value = null
    }
}
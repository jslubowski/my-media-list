package com.example.mymedialist.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MovieDetailsViewModel(
    application: Application,
    movieEntity: MovieEntity
// TODO (add datasource)
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedMovie = MutableLiveData<MovieEntity>()

    val selectedMovie: LiveData<MovieEntity>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movieEntity
    }

    //TODO add remove entity
}
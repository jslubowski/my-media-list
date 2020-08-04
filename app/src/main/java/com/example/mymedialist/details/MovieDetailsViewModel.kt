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
import java.time.LocalDate

class MovieDetailsViewModel(
    application: Application,
    private val movieEntity: MovieEntity,
    private val database: MovieRepository
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedMovie = MutableLiveData<MovieEntity>()
    val selectedMovie: LiveData<MovieEntity>
        get() = _selectedMovie

    private val _editAndNavigateToMainList = MutableLiveData<Boolean?>()
    val editAndNavigateToMainList: LiveData<Boolean?>
        get() = _editAndNavigateToMainList

    private val _editRatingButtonPressed = MutableLiveData<Boolean?>()
    val editRatingButtonPressed: LiveData<Boolean?>
        get() = _editRatingButtonPressed

    private val _editDateButtonPressed = MutableLiveData<Boolean?>()
    val editDateButtonPressed: LiveData<Boolean?>
        get() = _editDateButtonPressed

    init {
        _selectedMovie.value = movieEntity
    }

    fun editRating(value: Int) {
        movieEntity.rating = value
        update()
    }


    fun editDate(date: LocalDate) {
        movieEntity.seenOnDate = date
        update()
    }

    private fun update() {
        uiScope.launch {
            database.insertEntity(movieEntity)
        }
        _selectedMovie.value = movieEntity
    }

    fun onOkButtonClick() {
        _editAndNavigateToMainList.value = true
    }

    fun onEditRatingButtonClick() {
        _editRatingButtonPressed.value = true
    }

    fun onEditDateButtonClick() {
        _editDateButtonPressed.value = true
    }

    fun doneEditingDate() {
        _editDateButtonPressed.value = null
    }

    fun doneEditingRating() {
        _editRatingButtonPressed.value = null
    }

    fun doneNavigating() {
        _editAndNavigateToMainList.value = null
    }
}
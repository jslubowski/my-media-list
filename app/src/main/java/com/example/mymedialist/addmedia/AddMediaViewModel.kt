package com.example.mymedialist.addmedia

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

class AddMediaViewModel(
    val database: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private var addMediaJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + addMediaJob)

    private val _navigateToList = MutableLiveData<Boolean?>()
    val navigateToList: LiveData<Boolean?>
        get() = _navigateToList

    fun onAddButtonClick() {
        _navigateToList.value = true
    }

    fun addMovieToDatabase(movieEntity: MovieEntity) {
        uiScope.launch {
            database.insertEntity(movieEntity)
        }
    }

    fun doneNavigating() {
        _navigateToList.value = null
    }

}
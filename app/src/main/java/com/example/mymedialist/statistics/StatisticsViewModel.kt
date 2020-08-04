package com.example.mymedialist.statistics

import android.app.Application
import android.graphics.Movie
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.repository.MovieRepository
import com.example.mymedialist.util.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class StatisticsViewModel(
    private val database: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>>
        get() = _movies

    private val _fromDate = MutableLiveData<LocalDate>()
    val fromDate: LiveData<LocalDate>
        get() = _fromDate

    private val _toDate = MutableLiveData<LocalDate>()
    val toDate: LiveData<LocalDate>
        get() = _toDate

    private val _okButtonPressed = MutableLiveData<Boolean?>()
    val okButtonPressed: LiveData<Boolean?>
        get() = _okButtonPressed

    private val _fromDatePressed = MutableLiveData<Boolean?>()
    val fromDatePressed: LiveData<Boolean?>
        get() = _fromDatePressed

    private val _toDatePressed = MutableLiveData<Boolean?>()
    val toDatePressed: LiveData<Boolean?>
        get() = _toDatePressed


    @RequiresApi(Build.VERSION_CODES.O)
    fun getNumberOfMovies() {
        uiScope.launch {
            _movies.postValue(
                database.getMoviesWatchedInPeriod(_fromDate.value!!, _toDate.value!!)
            )
        }
    }

    fun setToDate(date: LocalDate) {
        _toDate.value = date
    }

    fun setFromDate(date: LocalDate) {
        _fromDate.value = date
    }

    fun onOkButtonPressed() {
        _okButtonPressed.value = true
    }

    fun doneNavigatingAfterOkButton() {
        _okButtonPressed.value = null
    }

    fun onFromDatePressed() {
        _fromDatePressed.value = true
    }

    fun donePickingFromDate() {
        _fromDatePressed.value = null
    }

    fun onToDatePressed() {
        _toDatePressed.value = true
    }

    fun donePickingToDate() {
        _toDatePressed.value = null
    }
}
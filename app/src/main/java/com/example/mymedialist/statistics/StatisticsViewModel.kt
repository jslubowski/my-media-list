package com.example.mymedialist.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.repository.MovieRepository
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

    private val _fromDate = MutableLiveData<LocalDate>()
    val fromDate: LiveData<LocalDate>
        get() =_fromDate

    private val _toDate = MutableLiveData<LocalDate>()
    val toDate: LiveData<LocalDate>
        get() =_toDate

    private val _okButtonPressed = MutableLiveData<Boolean?>()
    val okButtonPressed: LiveData<Boolean?>
        get() = _okButtonPressed

    fun getNumberOfMovies() {
        uiScope.launch {
            Timber.i(database.getMoviesWatchedInPeriod(_fromDate.value!!, _toDate.value!!).toString())
        }
    }
}
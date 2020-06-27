package com.example.mymedialist.additem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.network.Result
import com.example.mymedialist.repository.MovieRepository
import java.time.LocalDate

class AddItemViewModel(
    selectedItem: Result,
    val database: MovieRepository,
    application: Application
) : AndroidViewModel(application){

    private val _selectedMovie = MutableLiveData<Result>()
    val selectedMovie: LiveData<Result>
        get() = _selectedMovie

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate>
        get() = _selectedDate

    private val _setDatePressed = MutableLiveData<Boolean?>()
    val setDatePressed: LiveData<Boolean?>
        get() = _setDatePressed

    private val _rating = MutableLiveData<Int?>()
    val rating: LiveData<Int?>
        get() = _rating

    init {
        this._selectedMovie.value = selectedItem
    }

    fun setDateButtonPressed() {
        _setDatePressed.value = true
    }

    fun dateSet() {
        _setDatePressed.value = null
    }

    fun setSeenOnDate(date: LocalDate) {
        _selectedDate.value = date
    }

}
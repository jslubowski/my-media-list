package com.example.mymedialist.additem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.network.Result
import com.example.mymedialist.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddItemViewModel(
    private val selectedItem: Result,
    private val database: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    private var addItemJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + addItemJob)

    private val _selectedMovie = MutableLiveData<Result>()
    val selectedMovie: LiveData<Result>
        get() = _selectedMovie

    private val _selectedDate = MutableLiveData<LocalDate?>()
    val selectedDate: LiveData<LocalDate?>
        get() = _selectedDate

    private val _setDatePressed = MutableLiveData<Boolean?>()
    val setDatePressed: LiveData<Boolean?>
        get() = _setDatePressed

    private val _rating = MutableLiveData<Int?>()
    val rating: LiveData<Int?>
        get() = _rating

    private val _numberPickerPressed = MutableLiveData<Boolean?>()
    val numberPickerPressed: LiveData<Boolean?>
        get() = _numberPickerPressed

    private val _addButtonPressed = MutableLiveData<Boolean?>()
    val addButtonPressed: LiveData<Boolean?>
        get() = _addButtonPressed

    init {
        this._selectedMovie.value = selectedItem
    }

    fun setRating(rating: Int) {
        _rating.value = rating
    }

    fun setSeenOnDate(date: LocalDate) {
        _selectedDate.value = date
    }


    fun addItemToDatabase() {
        ioScope.launch {
            database.insertEntity(
                MovieEntity(
                    null,
                    selectedItem.title,
                    selectedItem.posterPath,
                    selectedItem.overview,
                    selectedItem.releaseDate,
                    _rating.value,
                    _selectedDate.value
                )
            )
        }
    }

    fun setDateButtonPressed() {
        _setDatePressed.value = true
    }

    fun numberPickerPressed() {
        _numberPickerPressed.value = true
    }

    fun addButtonPressed() {
        _addButtonPressed.value = true
    }

    fun datePicked() {
        _numberPickerPressed.value = null
    }

    fun ratingPickComplete() {
        _numberPickerPressed.value = null
    }

    fun addingComplete() {
        _addButtonPressed.value = null
    }

}
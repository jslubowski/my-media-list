package com.example.mymedialist.additem

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.network.Result
import com.example.mymedialist.repository.MovieRepository

class AddItemViewModelFactory(
    private val selectedItem: Result,
    private val dataSource: MovieRepository,
    private val application: Application
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            return AddItemViewModel(selectedItem, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
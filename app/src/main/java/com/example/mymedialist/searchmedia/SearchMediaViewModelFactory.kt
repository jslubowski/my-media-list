package com.example.mymedialist.searchmedia

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.repository.MovieRepository

class SearchMediaViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMediaViewModel::class.java)) {
            return SearchMediaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
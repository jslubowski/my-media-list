package com.example.mymedialist.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.mainlist.MainListViewModel
import com.example.mymedialist.model.MovieEntity

class MovieDetailsViewModelFactory(
    private val application: Application,
    private val movieEntity: MovieEntity
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application, movieEntity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
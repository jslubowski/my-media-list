package com.example.mymedialist.mainlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.dao.MovieDao
import com.example.mymedialist.titlescreen.TitleScreenViewModel
import javax.sql.DataSource

class MainListViewModelFactory(
    private val dataSource: MovieDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            return MainListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
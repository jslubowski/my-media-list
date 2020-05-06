package com.example.my_media_list.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainListViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            return MainListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
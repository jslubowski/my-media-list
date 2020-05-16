package com.example.mymedialist.titlescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TitleScreenViewModelFactory: ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleScreenViewModel::class.java)) {
            return TitleScreenViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
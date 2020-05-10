package com.example.mymedialist.titlescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleScreenViewModel : ViewModel() {

    private val _navigateToMainList = MutableLiveData<Boolean?>()
    val navigateToMainList: LiveData<Boolean?>
        get() = _navigateToMainList

    fun doneNavigating() {
        _navigateToMainList.value = null
    }

    fun onStartButtonClick() {
        _navigateToMainList.value = true
    }


}
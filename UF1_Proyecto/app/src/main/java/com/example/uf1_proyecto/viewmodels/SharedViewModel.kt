package com.example.uf1_proyecto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _bottomBarVisible = MutableLiveData<Boolean>()
    val bottomBarVisible: LiveData<Boolean> get() = _bottomBarVisible

    init {
        _bottomBarVisible.value = false
    }

    fun setBottomBarVisibility(isVisible: Boolean) {
        _bottomBarVisible.value = isVisible
    }
}
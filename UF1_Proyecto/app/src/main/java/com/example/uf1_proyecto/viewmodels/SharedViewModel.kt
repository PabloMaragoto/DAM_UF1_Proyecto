package com.example.uf1_proyecto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _BarVisible = MutableLiveData<Boolean>()
    val BarVisible: LiveData<Boolean> get() = _BarVisible



    init {
        _BarVisible.value = false
    }

    fun setBarVisibility(isVisible: Boolean) {
        _BarVisible.value = isVisible
    }
}
package com.example.managespending.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel() : ViewModel() {
    val drawerOpend = MutableLiveData<Boolean>()
    val add = MutableLiveData<Boolean>()
    fun showMenu(){
        drawerOpend.value = true
    }
    fun insertTrans(){
        add.value = true
    }
}
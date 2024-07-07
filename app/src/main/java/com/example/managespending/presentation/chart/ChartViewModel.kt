package com.example.managespending.presentation.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChartViewModel() : ViewModel (){
    val showPL = MutableLiveData<Boolean>()
    val navigateToMain = MutableLiveData<Boolean>()
    val showDate = MutableLiveData<Boolean>()
    val showView = MutableLiveData<Boolean>()
    fun showPhanLoai(){
        showPL.value = true
    }
    fun onBackClicked(){
        navigateToMain.value = true
    }
    fun showCalendar(){
        showDate.value = true
    }
    fun showViewBackground(){
        showView.value = true
    }
}
package com.example.managespending.presentation.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel() : ViewModel() {
    val navigateToMain = MutableLiveData<Boolean>()
    fun onBackClicked(){
        navigateToMain.value = true
    }
}
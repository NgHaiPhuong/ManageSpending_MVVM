package com.example.managespending.presentation.budget

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BudgetViewModel() : ViewModel() {
    var tvtotal = MutableLiveData<String>()
    var tvspend = MutableLiveData<String>()
    var tvlimit = MutableLiveData<String>()
    val navigateToMain = MutableLiveData<Boolean>()
    val showDialogInfor = MutableLiveData<Boolean>()
    val save = MutableLiveData<Boolean>()

    init{
        tvtotal.value = "10,000,000"
        tvspend.value = "250,000"
        tvlimit.value = "1,000,000"
    }
    fun onBackClicked(){
        navigateToMain.value = true
    }
    fun showDialog(){
        showDialogInfor.value = true
    }
    fun saveInfor(){
        save.value = true
    }
}
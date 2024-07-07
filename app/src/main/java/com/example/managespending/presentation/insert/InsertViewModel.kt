package com.example.managespending.presentation.insert

import androidx.compose.ui.graphics.Canvas
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managespending.model.Transaction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InsertViewModel() : ViewModel() {
    val tvname = MutableLiveData<String>()
    val tvdate = MutableLiveData<String>()
    val tvtime = MutableLiveData<String>()
    val tvnote = MutableLiveData<String>()
    val tvcost = MutableLiveData<String>()
    val navigateToMain = MutableLiveData<Boolean>()
    val showCalculator = MutableLiveData<Boolean>()
    val showBackground = MutableLiveData<Boolean>()
    val showList = MutableLiveData<Boolean>()
    val confirmSave = MutableLiveData<Boolean>()
    init {
        tvdate.value = getDate()
        tvtime.value = getTime()
    }
    fun showListCategory(){
        showList.value = true
    }
    fun showDimBackground(){
        showBackground.value = true
    }
    fun showCalculation(){
        showCalculator.value = true
    }
    fun getDate() : String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
    fun getTime(): String {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }
    fun onBackClicked(){
        navigateToMain.value = true
    }

    fun saveTrans() : Transaction {
        confirmSave.value = true
        return Transaction(0,
            tvname.value.toString(),
            "spend/car.png",
            "#ffffff",
            tvcost.value.toString().toFloat(),
            "spend",
            tvdate.value.toString(),
            tvtime.value.toString(),
            tvnote.value.toString())
    }
}
package com.example.managespending.presentation.detail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.model.Transaction
import com.example.managespending.presentation.home.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class DetailViewModel() : ViewModel() {
    val tvcost = MutableLiveData<String>()
    val tvcategory = MutableLiveData<String>()
    val tvdate = MutableLiveData<String>()
    private val tvnote = MutableLiveData<String>()
    val tvname = MutableLiveData<String>()
    private val imgicon = MutableLiveData<String>()
    private val tvtime = MutableLiveData<String>()
    val navigateToMain = MutableLiveData<Boolean>()
    val confirmUpdate = MutableLiveData<Boolean>()
    val confirmDelete = MutableLiveData<Boolean>()
    init{
        tvnote.value = "No data"
        tvname.value = ""
    }
    fun onBackClicked(){
        navigateToMain.value = true
    }
    fun deleteTransaction(
        transactionId: Int,
        listTransaction: MutableList<Transaction>
    ) : MutableList<Transaction>{
        listTransaction.forEach {item->
            if(item.id.equals(transactionId)){
                listTransaction.remove(item)
            }
        }
        listTransaction.forEach {item->
            Log.d("DeleteTransaction", "Deleting transaction with id: ${item.name} + ${item.id}")
        }
        confirmDelete.value = true
        return listTransaction
    }
    fun updateTransaction(
        transactionId: Int,
        listTransaction: MutableList<Transaction>
    ) : MutableList<Transaction>{
        listTransaction.forEach { item ->
            if (item.id == transactionId) {
                item.name = tvname.value.toString()
                item.cost = tvcost.value.toString().toFloat()
                item.category = tvcategory.value.toString()
                item.date = tvdate.value.toString()
                item.note = tvnote.value.toString()
                item.icon = imgicon.value.toString()
                item.time = tvtime.value.toString()
                item.backgroundColor = "#ffffff"
            }
        }

        confirmUpdate.value = true
        return listTransaction
    }
}
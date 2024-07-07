package com.example.managespending.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.db.dao.MyDao

class MyViewModelFactory(private val dao: MyDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyViewModel::class.java))
            return MyViewModel(dao) as T

        throw IllegalArgumentException("Unknown class for view model")
    }

}
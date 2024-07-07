package com.example.managespending.db.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.managespending.db.dao.MyDao
import com.example.managespending.db.database.GetList
import com.example.managespending.model.Category
import com.example.managespending.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val dao: MyDao) : ViewModel(){
    val allCategoryList = dao.getAllCategory()
    val allTransactionList = dao.getAllTransaction()
    val category : MutableLiveData<List<Category>?> = MutableLiveData()

    fun addCategory(newItem : Category) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.insertCategory(newItem)
        }
    }
    fun addAllCategory(newList : List<Category>) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.insertAllCategory(newList)
        }
    }
    fun addTransaction(newItem:Transaction) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.insertTransaction(newItem)
        }
    }
    fun addAllTransaction(newItem:List<Transaction>) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.insertAllTransaction(newItem)
        }
    }
    fun deleteCategory(id : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.deleteCategoryById(id)
        }
    }
    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.deleteTransactionById(transactionId)
            }
        }
    }
    fun updateTransaction(itemList : Transaction) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.updateTransaction(itemList)
        }
    }

    fun deleteAllTransaction() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.deleteAllTransaction()
        }
    }

    fun deleteAllCategory() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            dao.deleteAllCategories()
        }
    }
}
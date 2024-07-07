package com.example.managespending.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.managespending.model.Category
import com.example.managespending.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Insert
    suspend fun insertAllCategory(list : List<Category>)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM category_table WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)

    @Query("DELETE FROM category_table")
    fun deleteAllCategories()

    @Query("SELECT * FROM category_table")
    fun getAllCategory() : LiveData<List<Category>>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Insert
    suspend fun insertAllTransaction(list : List<Transaction>)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM transaction_table WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: Int)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllTransaction()

    @Query("SELECT * FROM transaction_table")
    fun getAllTransaction() : LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE date IN (" +
            "SELECT date FROM transaction_table GROUP BY date)")
    fun getTransactionByDate() : LiveData<List<Transaction>>
}
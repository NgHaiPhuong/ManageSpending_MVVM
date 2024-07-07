package com.example.managespending.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.managespending.db.dao.MyDao
import com.example.managespending.model.Category
import com.example.managespending.model.Transaction

@Database(entities = [Category::class, Transaction::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){
    abstract fun myDao() : MyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
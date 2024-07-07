package com.example.managespending.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "transaction_name")
    var name: String,
    var icon: String,
    var backgroundColor: String,
    var cost: Float,
    var category: String,
    var date: String,
    var time: String,
    var note: String,
)
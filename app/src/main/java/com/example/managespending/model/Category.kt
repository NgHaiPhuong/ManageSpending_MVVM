package com.example.managespending.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name : String,
    var icon : String,
    var backgroundColor : String,
    var classify : String,
)
package com.example.managespending.db.database

import com.example.managespending.model.Category
import com.example.managespending.model.Transaction
import kotlinx.coroutines.delay

object GetList{
    suspend fun addData() : List<Category>{
        delay(5000)
        return arrayListOf(
            Category(1, "Build", "spend/build.png", "#ffffff", "spend"),
            Category(2, "Car", "spend/car.png", "#ffffff", "spend"),
            Category(3, "Cinema", "spend/cinema.png", "#ffffff", "spend"),
            Category(4, "Clothes", "spend/clothes.png", "#ffffff", "spend"),
            Category(5, "Drink", "spend/drink.png", "#ffffff", "spend"),

            Category(6, "Coupon", "income/coupon.png", "#ffffff", "income"),
            Category(7, "Discount", "income/discount.png", "#ffffff", "income"),
            Category(8, "Salary", "income/salary.png", "#ffffff", "income"),
        )
    }

    fun addDataTran() : List<Transaction>{
        return arrayListOf(
            Transaction(1, "Sell", "income/sell.png", "#ffffff", 1000000f, "Income", "10/6/2024", "15:30", "Sell food a day"),
            Transaction(2, "Car", "spend/car.png", "#ffffff", 65000f, "Spend", "10/6/2024", "15:30", "Accident"),
            Transaction(3, "Study", "spend/study.png", "#ffffff", 20000f, "Spend", "11/6/2024", "15:30", "Education for children"),
            Transaction(4, "Electric", "spend/study.png", "#ffffff", 20000f, "Spend", "12/6/2024", "15:30", "Electric money for family"),
        )
    }
}
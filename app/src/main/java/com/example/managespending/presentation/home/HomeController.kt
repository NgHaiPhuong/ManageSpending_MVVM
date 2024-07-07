package com.example.managespending.presentation.home

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.example.managespending.itemCard
import com.example.managespending.itemHeader
import com.example.managespending.itemInfor
import com.example.managespending.itemLoading
import com.example.managespending.itemTransaction
import com.example.managespending.model.Transaction
import com.example.managespending.presentation.detail.DetailActivity
import com.example.managespending.presentation.budget.BudgetActivity
import com.example.managespending.util.FormatNumberUtil
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class HomeController : EpoxyController() {
    var listTransaction : MutableList<Transaction> = ArrayList()
        set(value) {
            field.clear()  // Xóa các phần tử hiện có
            field.addAll(value)
            field = value
            requestModelBuild()
        }
    val groupedTransactions = mutableMapOf<String, MutableList<Transaction>>()
    var income1 : Float = 0f
        set(value) {
            field = value
            requestModelBuild()
        }
    var limit : Float = 0f
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory(){
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return GravitySnapHelper(Gravity.CENTER)
            }
        })

        itemHeader {
            id("item_header")
            date(this@HomeController.getDate())
            spanSizeOverride { totalSpanCount, position, itemCount ->
                totalSpanCount
            }
        }

        itemTransaction {
            id("item_transaction")
            balance(this@HomeController.getBalance())
            expand(this@HomeController.getExpend())
            income(this@HomeController.getIncome())
            arms(this@HomeController.limit.toString())
            onClick(View.OnClickListener {
                val context = it.context
                val intent = Intent(context, BudgetActivity::class.java)
                context.startActivity(intent)
            })
            spanSizeOverride { totalSpanCount, position, itemCount ->
                totalSpanCount
            }
        }

        if(listTransaction.isEmpty()) {
            itemLoading {
                id("loading_list_1")
                spanSizeOverride { totalSpanCount, position, itemCount ->
                    totalSpanCount
                }
            }
        }
        else{
            listTransaction.forEach { transaction ->
                val date = transaction.date
                if (!groupedTransactions.containsKey(date)) {
                    groupedTransactions[date] = mutableListOf()
                }
                groupedTransactions[date]?.add(transaction)
            }
            groupedTransactions.forEach{(date, transaction) ->
                var totalIncome : Float = 0f
                var totalSpend : Float = 0f
                transaction.forEach { item->
                    if(item.category.equals("Income", ignoreCase = true)){
                        totalIncome += item.cost
                    }
                    if(item.category.equals("Spend", ignoreCase = true)){
                        totalSpend += item.cost
                    }
                }
                val total = totalIncome - totalSpend
                val total1 = FormatNumberUtil.format(total)
                itemCard {
                    id("item_card_$date")
                    date(date)
                    total(total1)
                    spanSizeOverride { totalSpanCount, _, _ ->
                        totalSpanCount
                    }
                }
                transaction.forEach { item ->
                    val cost = FormatNumberUtil.format(item.cost)
                    val cost1 : String
                    if(item.category.equals("Spend", ignoreCase = true)){
                        cost1 = "-$cost"
                    }
                    else{
                        cost1 = cost
                    }
                    itemInfor {
                        id("item_infor_${item.id}")
                        url(item.icon)
                        name(item.name)
                        cost(cost1)
                        onClick(View.OnClickListener {
                            val context = it.context
                            val intent = Intent(context, DetailActivity::class.java).apply {
                                putExtra("id", item.id)
                                putExtra("url", item.icon)
                                putExtra("name", item.name)
                                putExtra("cost", item.cost)
                                putExtra("category", item.category)
                                putExtra("date", item.date)
                                putExtra("note", item.note)
                                putExtra("time", item.time)
                            }
                            context.startActivity(intent)
                        })
                        spanSizeOverride { totalSpanCount, _, _ ->
                            totalSpanCount
                        }
                    }

                }
            }

        }

    }

    fun getBalance() : String{
        var income : Float = 0f
        var expend : Float = 0f
        listTransaction.forEach { item ->
            if(item.category.contains("income", true)){
                income += item.cost
            }
            else if(item.category.contains("spend", true)){
                expend += item.cost
            }
        }
        return FormatNumberUtil.format((income - expend))
    }

    fun getIncome() : String {
        var money : Float = 0f
        listTransaction.forEach { item ->
            if(item.category.contains("income", true)){
                money += item.cost
            }
        }
        return FormatNumberUtil.format(money + income1)
    }

    fun getExpend() : String {
        var money : Float = 0f
        listTransaction.forEach { item->
            if(item.category.contains("spend", true)){
                money += item.cost
            }
        }
        return FormatNumberUtil.format(money)
    }

    fun getDate() : String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$day/$month/$year"
    }
}
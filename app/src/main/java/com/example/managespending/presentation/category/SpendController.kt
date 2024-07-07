package com.example.managespending.presentation.category

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.example.managespending.itemIncome
import com.example.managespending.itemSpend
import com.example.managespending.model.Category
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class SpendController : EpoxyController() {
    var listCategory : MutableList<Category> = ArrayList()
        set(value) {
            field.clear()
            field.addAll(value)
            field = value
            requestModelBuild()
        }
    override fun buildModels() {
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory(){
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return GravitySnapHelper(Gravity.CENTER)
            }
        })

        listCategory.forEachIndexed { index, item ->
            if(item.classify.contains("spend", true)) {
                itemSpend {
                    id("spend $index")
                    url(item.icon)
                    name(item.name)
                    onClick(View.OnClickListener {

                    })
                    spanSizeOverride { totalSpanCount, position, itemCount ->
                        totalSpanCount
                    }
                }
            }
        }
    }
}
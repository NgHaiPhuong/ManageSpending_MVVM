package com.example.managespending.presentation.insert

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.example.managespending.itemIncome
import com.example.managespending.itemSpend
import com.example.managespending.itemTitle
import com.example.managespending.itemTransaction
import com.example.managespending.model.Category
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class InsertController : EpoxyController() {
    var listCategory: MutableList<Category> = ArrayList()
        set(value) {
            field.clear()  // Xóa các phần tử hiện có
            field.addAll(value)
            field = value
            requestModelBuild()
        }

    var show : Boolean = true

    override fun buildModels() {
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return GravitySnapHelper(Gravity.CENTER)
            }
        })

        itemTitle {
            id("list 1")
            onClick1(View.OnClickListener {
                this@InsertController.show = true
            })
            onClick2 (View.OnClickListener {
                this@InsertController.show = false
            })
            spanSizeOverride { totalSpanCount, position, itemCount ->
                totalSpanCount
            }
        }
        if(show){
            listCategory.forEachIndexed { index, item ->
                if (item.classify.contains("spend", true)) {
                    itemSpend {
                        id("list_$index")
                        name(item.name)
                        url(item.icon)
                        spanSizeOverride { totalSpanCount, position, itemCount ->
                            totalSpanCount
                        }
                    }
                }
            }
        }
        else {
            listCategory.forEachIndexed { index, item ->
                if (item.classify.contains("income", true)) {
                    itemSpend {
                        id("list_$index")
                        name(item.name)
                        url(item.icon)
                        spanSizeOverride { totalSpanCount, position, itemCount ->
                            totalSpanCount
                        }
                    }
                }
            }
        }

    }
}
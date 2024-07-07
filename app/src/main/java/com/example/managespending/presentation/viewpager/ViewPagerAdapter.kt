package com.example.managespending.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.managespending.presentation.category.CategoryFragment
import com.example.managespending.presentation.category.IncomeFragment
import com.example.managespending.presentation.category.SpendFragment


class ViewPagerAdapter(fm: CategoryFragment) : FragmentStateAdapter(fm){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpendFragment()
            1 -> IncomeFragment()
            else -> Fragment()
        }
    }
}

class ViewPagerAdapter2(fa:FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpendFragment()
            1 -> IncomeFragment()
            else -> Fragment()
        }
    }
}

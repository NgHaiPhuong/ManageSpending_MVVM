package com.example.managespending.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.managespending.presentation.chart.BarFragment
import com.example.managespending.presentation.chart.ChartFragment
import com.example.managespending.presentation.chart.LineFragment
class ViewPagerAdapter_Chart (fm : ChartFragment) : FragmentStateAdapter(fm){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BarFragment()
            1 -> LineFragment()
            else -> Fragment()
        }
    }
}
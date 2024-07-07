package com.example.managespending.presentation.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.managespending.R
import com.example.managespending.base.BaseActivity
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.presentation.category.CategoryFragment
import com.example.managespending.presentation.chart.ChartFragment
import com.example.managespending.presentation.home.HomeFragment

class MainActivity : BaseActivity() {
    private lateinit var bottomNavigation : MeowBottomNavigation
    private var selectedItemId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        setupView()
        handleEvent()
    }

    fun handleBackpress() {
        replaceFragment(HomeFragment())
        selectedItemId = 1
    }

    private fun handleEvent() {

    }

    fun toFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun displayBottomNav(){
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.category_noactive))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.home_notactive))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.chart_noactive))

        bottomNavigation.setOnShowListener { model ->
            when (model.id) {
                1 -> {
                   // updateBottomNav(1)
                    replaceFragment(CategoryFragment())
                }
                2 -> {
                   // updateBottomNav(2)
                    replaceFragment(HomeFragment())
                }
                3 -> {
                  //  updateBottomNav(3)
                    replaceFragment(ChartFragment())
                }
            }
            Unit
        }
        bottomNavigation.show(2, true)
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupView() {
        val bundle = intent.extras ?: Bundle().apply {
            putString("money1", "0.0")
            putString("limit1", "0.0")
        }
        val homeFragment = HomeFragment.newInstance()
        homeFragment.arguments = bundle
        replaceFragment(homeFragment)
        displayBottomNav()
    }

    private fun initData() {

    }
}
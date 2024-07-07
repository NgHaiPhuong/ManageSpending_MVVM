package com.example.managespending.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managespending.util.ThemeUtil

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeUtil.getTheme(this@BaseActivity))
    }

}
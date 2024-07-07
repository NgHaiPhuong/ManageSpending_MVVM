package com.example.managespending.app

import android.app.Application
import com.example.managespending.preference.MyPreferences

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MyPreferences.init(this@MyApplication)
        //MyPreferences.write(MyPreferences.PREF_COMPLETE_TASKS_TODAY, 1)
        //MyPreferences.read(MyPreferences.PREF_COMPLETE_TASKS_TODAY, 1)
    }
}
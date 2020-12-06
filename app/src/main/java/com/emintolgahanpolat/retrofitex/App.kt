package com.emintolgahanpolat.retrofitex

import android.app.Application
import com.emintolgahanpolat.retrofitex.data.AppPreferences

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        AppToast.init(this)

    }
}
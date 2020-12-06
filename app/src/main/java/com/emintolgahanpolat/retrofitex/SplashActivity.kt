package com.emintolgahanpolat.retrofitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emintolgahanpolat.retrofitex.data.AppPreferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppPreferences.init(this)
        if(!AppPreferences.token.isNullOrBlank()){
            finishAndStartActivityIntent(MainActivity::class.java)
        }else{
            finishAndStartActivityIntent(LoginActivity::class.java)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
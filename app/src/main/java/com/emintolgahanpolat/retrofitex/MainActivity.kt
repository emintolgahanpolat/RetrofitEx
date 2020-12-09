package com.emintolgahanpolat.retrofitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emintolgahanpolat.retrofitex.connection.ApiResult
import com.emintolgahanpolat.retrofitex.connection.RetrofitBuilder
import com.emintolgahanpolat.retrofitex.connection.enqueue
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import com.emintolgahanpolat.retrofitex.data.getTokens
import com.emintolgahanpolat.retrofitex.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginDataLbl.text = AppPreferences.getTokens()

        refreshTokenBtn.setOnClickListener {
            refreshToken()
        }


        testServiceBtn.setOnClickListener {
            serviceDataLbl.text = ""

            fetchUserDetail{
                serviceDataLbl.text = "\n${it}\n"
            }


        }
        removeTokenBtn.setOnClickListener {
            AppPreferences.token = null
            showToast("Removed Token")
        }

        removeRefreshTokenBtn.setOnClickListener {
            AppPreferences.token = null
            AppPreferences.refreshToken = null
            showToast("Removed Refresh Token")
        }
    }

    private fun fetchUserDetail(callback: (User?) -> Unit) {
        callback(AppPreferences.user)
        RetrofitBuilder.instance.user().enqueue(this){
            AppPreferences.user = it.response
            it.response?.let(callback)
        }
    }

    private fun refreshToken() {
        RetrofitBuilder.instance.refreshToken("Bearer ${AppPreferences.refreshToken}")
                .enqueue(this) {
                    if (it.response != null) {
                        AppPreferences.refreshToken = it.response?.refreshToken
                        AppPreferences.token = it.response?.token
                        loginDataLbl.text =
                                "Token :\n ${it.response?.token}\n\nRefresh Token :\n${it.response?.refreshToken}"

                    } else {
                        it.error?.message?.let {
                            showToast(it)
                        }
                    }
                }
    }

}
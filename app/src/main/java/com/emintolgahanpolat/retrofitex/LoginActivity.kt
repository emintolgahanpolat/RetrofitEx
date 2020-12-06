package com.emintolgahanpolat.retrofitex

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emintolgahanpolat.retrofitex.connection.RetrofitBuilder
import com.emintolgahanpolat.retrofitex.connection.enqueue
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import com.emintolgahanpolat.retrofitex.model.Login
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fetchCaptcha()

        refreshCaptchaImgBtn.setOnClickListener {
            fetchCaptcha()
        }
        loginBtn.setOnClickListener {
            loginAction()
        }


    }


    private fun fetchCaptcha() {
        refreshCaptchaImgBtn.isEnabled = false
        RetrofitBuilder.instance.captcha().enqueue(this) { response, error ->
            refreshCaptchaImgBtn.isEnabled = true
            if (response != null) {

                captchaImgV.base64(response.captcha)

            } else {
                showToast(error?.message)
            }
        }
    }

    private fun loginAction() {


        RetrofitBuilder.instance.login("${captchaEt.text}", Login("${usernameEt.text}", "${passwordEt.text}"))
                .enqueue(this) { response, error ->
                    if (response != null) {
                        AppPreferences.refreshToken = response.refreshToken
                        AppPreferences.token = response.token
                        finishAndStartActivityIntent(MainActivity::class.java)
                    } else {
                        fetchCaptcha()
                        showToast(error?.message)
                    }
                }
    }
}


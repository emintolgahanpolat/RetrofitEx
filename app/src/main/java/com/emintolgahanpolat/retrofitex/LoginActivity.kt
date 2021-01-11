package com.emintolgahanpolat.retrofitex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
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



        titleText.text =  "Merhaba Dünya Hello".toSpannable().boldText(0,5).underline(3,6).foregroundColorSpan(this.getColor(R.color.switch_on),4,8)

    }


    private fun fetchCaptcha() {
        refreshCaptchaImgBtn.isEnabled = false
        RetrofitBuilder.instance.captcha().enqueue(this) {
            refreshCaptchaImgBtn.isEnabled = true
            if (it.response != null) {

                captchaImgV.base64(it.response?.captcha!!)

            } else {
                showToast(it.error?.message)
            }
        }
    }

    private fun loginAction() {


        RetrofitBuilder.instance.login("${captchaEt.text}", Login("${usernameEt.text}", "${passwordEt.text}"))
                .enqueue(this) {

                        if (it.response != null) {
                            AppPreferences.refreshToken = it.response?.refreshToken
                            AppPreferences.token = it.response?.token
                            finishAndStartActivityIntent(MainActivity::class.java)
                        } else {
                            fetchCaptcha()
                            showToast(it.error?.message)
                        }


                }
    }
}



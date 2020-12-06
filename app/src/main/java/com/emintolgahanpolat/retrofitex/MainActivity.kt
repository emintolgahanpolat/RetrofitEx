package com.emintolgahanpolat.retrofitex

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emintolgahanpolat.retrofitex.connection.RetrofitBuilder
import com.emintolgahanpolat.retrofitex.connection.enqueue
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginDataLbl.text =
                "Token :\n ${AppPreferences.token}\n\nRefresh Token :\n${AppPreferences.refreshToken}"

        refreshTokenBtn.setOnClickListener {
            refreshToken()
        }

        testServiceBtn.setOnClickListener {
            serviceDataLbl.text = ""
            fetchUserDetail {
                serviceDataLbl.text = "\n${serviceDataLbl.text}\n0\n$it\n"
            }
            /*
            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n1\n$it\n"
                }
            }

            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n2\n$it\n"
                }
            }

            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n3\n$it\n"
                }
            }

            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n4\n$it\n"
                }
            }

            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n5\n$it\n"
                }
            }

            thread {
                fetchUserDetail {
                    serviceDataLbl.text = "\n${serviceDataLbl.text}\n6\n$it\n"
                }
            }

             */

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

    private fun fetchUserDetail(callback: (String?) -> Unit) {

        RetrofitBuilder.instance.user().enqueue(this) { response, error ->

            if (response != null) {
                //serviceDataLbl.text = "User :\n$response"

                callback(response.toString())
            } else {
                showToast(error?.message)
            }
        }
    }

    private fun refreshToken() {
        RetrofitBuilder.instance.refreshToken("Bearer ${AppPreferences.refreshToken}")
                .enqueue(this) { response, error ->
                    if (response != null) {

                        AppPreferences.refreshToken = response.refreshToken
                        AppPreferences.token = response.token
                        loginDataLbl.text =
                                "Token :\n ${response.token}\n\nRefresh Token :\n${response.refreshToken}"

                    } else {
                        showToast(error?.message)
                    }
                }
    }

}
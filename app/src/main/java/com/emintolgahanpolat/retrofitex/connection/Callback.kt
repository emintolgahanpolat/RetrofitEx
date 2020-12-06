package com.emintolgahanpolat.retrofitex.connection


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.emintolgahanpolat.retrofitex.SplashActivity
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import com.emintolgahanpolat.retrofitex.model.ApiError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class MyCallback<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        error(
                ApiError(
                        "",
                        10001,
                        t.localizedMessage,
                        t.localizedMessage,
                        call.request().url().encodedPath(),
                        mutableListOf<String>()
                )
        )
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {

            success(response)

        } else {
            val error = Gson().fromJson(response.errorBody()?.string(), ApiError::class.java)
            error(error)
        }
    }

    abstract fun success(response: Response<T>)
    abstract fun error(error: ApiError?)
}


inline fun <reified T> Call<T>.enqueue(
        context: Activity? = null,
        crossinline result: (response: T?, error: ApiError?) -> Unit
) {
    enqueue(object : MyCallback<T>() {
        override fun success(response: Response<T>) {

            result(response.body(), null)
        }

        override fun error(error: ApiError?) {
            if (error?.status == 401) {
                context?.logout()
            }
            context?.let {
                error?.let {
                    AlertDialog.Builder(context)
                            .setTitle(error.error)
                            .setMessage(error.message)
                            .setNegativeButton("Tamam", null)
                            .show()
                }

            }
            result(null, error)
        }

    })
}

fun Activity.logout() {
    AppPreferences.token = null
    AppPreferences.refreshToken = null
    val intent = Intent(this, SplashActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}
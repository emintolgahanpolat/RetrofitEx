package com.emintolgahanpolat.retrofitex.connection


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.emintolgahanpolat.retrofitex.SplashActivity
import com.emintolgahanpolat.retrofitex.createLoadingDialog
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

data class ApiResult<T>(var response: T?,var error: ApiError?)




inline fun <reified T> Call<T>.enqueue(
    context: Activity? = null,
    crossinline result: (ApiResult<T>) -> Unit
) {
    val dialog = context?.createLoadingDialog()
    dialog?.show()
    enqueue(object : MyCallback<T>() {
        override fun success(response: Response<T>) {

            result(ApiResult(response.body(),null))
            dialog?.dismiss()
        }

        override fun error(error: ApiError?) {

            context?.let {
                error?.let {
                    AlertDialog.Builder(context)
                        .setTitle(error.error)
                        .setMessage(error.message)
                        .setNegativeButton("Tamam", null)
                        .show()
                }

            }
            result(ApiResult(null,error))
            dialog?.dismiss()
        }

    })
}

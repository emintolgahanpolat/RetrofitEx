package com.emintolgahanpolat.retrofitex.data

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private const val COUNTER = "COUNTER"
    private const val TOKEN = "TOKEN"
    private const val TOKEN_REFRESH = "TOKEN_REFRESH"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    var counter: Int
        get() = preferences.get(COUNTER, -1)
        set(value) = preferences.setValue(COUNTER,value)

    var token: String?
        get() = preferences.getString(TOKEN, null)
        set(value) = preferences.setValue(TOKEN,value)

    var refreshToken: String?
        get() = preferences.getString(TOKEN_REFRESH, null)
        set(value) = preferences.setValue(TOKEN_REFRESH,value)
}
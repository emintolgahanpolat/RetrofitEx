package com.emintolgahanpolat.retrofitex.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.emintolgahanpolat.retrofitex.BuildConfig
import com.emintolgahanpolat.retrofitex.model.User

object AppPreferences {
    private const val NAME = BuildConfig.APPLICATION_ID + "SHARED_PREFERENCES"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private const val COUNTER = "COUNTER"
    private const val TOKEN = "TOKEN"
    private const val TOKEN_REFRESH = "TOKEN_REFRESH"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    var user: User?
        get() = preferences.getObject(COUNTER, User::class.java)
        set(value) = preferences.setValue(COUNTER, value)

    var token: String?
        get() = preferences.getString(TOKEN, null)
        set(value) = preferences.setValue(TOKEN, value)

    var refreshToken: String?
        get() = preferences.getString(TOKEN_REFRESH, null)
        set(value) = preferences.setValue(TOKEN_REFRESH, value)


}


fun AppPreferences.getTokens(): String? {
    return "Token :\n$token\n\nRefresh Token :\n$refreshToken"
}

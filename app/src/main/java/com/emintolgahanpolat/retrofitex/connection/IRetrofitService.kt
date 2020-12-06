package com.emintolgahanpolat.retrofitex.connection

import com.emintolgahanpolat.retrofitex.model.Captcha
import com.emintolgahanpolat.retrofitex.model.Login
import com.emintolgahanpolat.retrofitex.model.LoginResponse
import com.emintolgahanpolat.retrofitex.model.User
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IRetrofitService {

    @POST("/login")
    fun login(@Header("captcha") captcha:String, @Body login: Login): Call<LoginResponse>

    @GET("/captcha")
    fun captcha(): Call<Captcha>

    @GET("/refresh")
    fun refreshToken(@Header("Authorization") refreshToken: String?): Call<LoginResponse>


    @GET("/user")
    fun user(): Call<User>
}
package com.emintolgahanpolat.retrofitex.connection

import com.emintolgahanpolat.retrofitex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    val logging = HttpLoggingInterceptor()

    init {
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
    }

    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(AuthenticationInterceptorRefreshToken())
        .build()


    private const val BASE_URL = BuildConfig.API_ADDRESS
    private var retrofit: Retrofit? = null
    val instance: IRetrofitService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(IRetrofitService::class.java)
        }
}
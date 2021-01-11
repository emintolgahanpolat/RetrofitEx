package com.emintolgahanpolat.retrofitex.connection

import android.util.Log
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class AuthenticationInterceptorRefreshToken  : Interceptor {
   // private val locks: Lock = ReentrantLock()

    override fun intercept(chain: Interceptor.Chain): Response? {
        //MAKE SYNCHRONIZED

        synchronized(this) {
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${AppPreferences.refreshToken}").build()
            val initialResponse = chain.proceed(authenticationRequest)
            when {

                initialResponse.code() == 403  || initialResponse.code() == 401  -> {

                    //RUN BLOCKING!!
                   // locks.lock()
                    val responseNewTokenLoginModel =// runBlocking {
                      RetrofitBuilder.instance.refreshToken("").execute()
                 //   }

                    return when {
                        responseNewTokenLoginModel.code() != 200 -> {

                            AppPreferences.logout()

                           //logout
                            initialResponse
                        }
                        else -> {
                            //locks.unlock()
                            responseNewTokenLoginModel.body()?.let {

                                Log.i("Refresh Token","Token Yenilendi")
                                AppPreferences.token = it.token
                                AppPreferences.refreshToken = it.refreshToken
                            }
                            val newAuthenticationRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.token}").build()
                            chain.proceed(newAuthenticationRequest)
                        }
                    }
                }
                else -> return initialResponse
            }
        }
    }

}
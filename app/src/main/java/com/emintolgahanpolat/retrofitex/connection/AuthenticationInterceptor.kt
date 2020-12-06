package com.emintolgahanpolat.retrofitex.connection

import android.util.Log
import com.emintolgahanpolat.retrofitex.AppToast
import com.emintolgahanpolat.retrofitex.data.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
/*
class AuthenticationInterceptorRefreshToken  : Interceptor {

    override fun intercept(chain: Interceptor.Chain) : Response {
        val originalRequest = chain.request()
        val authenticationRequest = request(originalRequest)
        val initialResponse = chain.proceed(authenticationRequest)

        when {
            initialResponse.code() == 500 ||  initialResponse.code() == 403 || initialResponse.code() == 401 -> {
                RetrofitBuilder.instance.refreshToken("Bearer ${AppPreferences.refreshToken}")
                    .enqueue { isSuccess, response, error ->

                        if (isSuccess) {
                            response?.let {
                                AppPreferences.token = it.token
                                AppPreferences.refreshToken = it.refreshToken




                                val newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer ${it.token}").build()

                                chain.proceed(newRequest)
                            }

                        } else {
                            AppToast.show("Oturumu Kapat")
                        }
                    }
            }
            else -> return initialResponse
        }
        return initialResponse
    }
    private fun request(originalRequest: Request): Request {
        return originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${AppPreferences.token}").build()

    }
}
*/
class AuthenticationInterceptorRefreshToken  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        //MAKE SYNCHRONIZED
        synchronized(this) {
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${AppPreferences.refreshToken}").build()
            val initialResponse = chain.proceed(authenticationRequest)
            if (AppPreferences.refreshToken.isNullOrBlank()) {
                return initialResponse
            }
            when {

                initialResponse.code() == 500 || initialResponse.code() == 403  -> {
                    //RUN BLOCKING!!
                    val responseNewTokenLoginModel =// runBlocking {
                        //AppPreferences.refreshToken
                      RetrofitBuilder.instance.refreshToken("").execute()
                 //   }

                    return when {
                        responseNewTokenLoginModel == null || responseNewTokenLoginModel.code() != 200 -> {
                            AppToast.show("Log Out")

                           //logout
                            null
                        }
                        else -> {
                            responseNewTokenLoginModel.body()?.let {
                                AppToast.show("Refresh Token")
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
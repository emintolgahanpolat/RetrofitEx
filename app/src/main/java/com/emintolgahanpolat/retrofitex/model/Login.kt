package com.emintolgahanpolat.retrofitex.model


data class Login(val username:String,val password:String)
data class LoginResponse(val token:String,val refreshToken:String)


data class Captcha(val captcha: String)



data class User (
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val authorities: List<Authority>,
    val enabled: Boolean
)

data class Authority (val authority: String)


data class ApiError (
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val errors: List<Any?>
)
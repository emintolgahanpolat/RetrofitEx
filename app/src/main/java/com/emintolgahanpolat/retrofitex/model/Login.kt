package com.emintolgahanpolat.retrofitex.model


data class Login(val username:String,val password:String)
data class LoginResponse(val token:String,val refreshToken:String)


data class Captcha(val captcha: String)



data class User (
    var username: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var authorities: List<Authority>,
    var enabled: Boolean
)

data class Authority (val authority: String)


data class ApiError (
    var timestamp: String,
    var status: Int,
    var error: String,
    var message: String,
    var path: String,
    var errors: List<Any?>
)
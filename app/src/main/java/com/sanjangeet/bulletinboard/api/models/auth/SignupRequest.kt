package com.sanjangeet.bulletinboard.api.models.auth

data class SignupRequest (
    val full_name: String,
    val email: String,
    val password: String,
    val otp: String
)

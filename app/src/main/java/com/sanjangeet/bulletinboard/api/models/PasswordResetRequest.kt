package com.sanjangeet.bulletinboard.api.models

data class PasswordResetRequest (
    val email: String,
    val otp: String,
    val password: String
)

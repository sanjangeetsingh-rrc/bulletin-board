package com.sanjangeet.bulletinboard.api.models.user

data class PasswordResetRequest (
    val email: String,
    val otp: String,
    val password: String
)

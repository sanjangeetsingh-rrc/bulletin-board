package com.sanjangeet.bulletinboard.api.models.auth

data class LoginRequest(
    val email: String,
    val password: String
)

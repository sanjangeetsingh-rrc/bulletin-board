package com.sanjangeet.bulletinboard.api.models.auth

data class LoginResponse(
    val refresh: String,
    val access: String
)

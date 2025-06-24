package com.sanjangeet.bulletinboard.api.models

data class PasswordChangeRequest (
    val old_password: String,
    val new_password: String
)

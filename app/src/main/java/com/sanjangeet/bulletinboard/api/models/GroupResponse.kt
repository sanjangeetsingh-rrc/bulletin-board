package com.sanjangeet.bulletinboard.api.models

data class GroupResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val icon: String?,
    val admin_name: String,
    val created_at: String
)

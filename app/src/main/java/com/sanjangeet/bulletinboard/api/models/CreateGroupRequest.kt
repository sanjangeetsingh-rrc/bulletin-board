package com.sanjangeet.bulletinboard.api.models

data class CreateGroupRequest(
    val name: String,
    val description: String? = null,
    val icon: String? = null,
    val whitelist: List<String>? = null,
    val blacklist: List<String>? = null
)

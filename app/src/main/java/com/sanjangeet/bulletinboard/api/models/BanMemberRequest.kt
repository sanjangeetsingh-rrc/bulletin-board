package com.sanjangeet.bulletinboard.api.models

data class BanMemberRequest(
    val group: Int,
    val user: Int
)

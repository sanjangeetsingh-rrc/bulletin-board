package com.sanjangeet.bulletinboard.api.models.group

data class BanMemberRequest(
    val group: Int,
    val user: Int
)

package com.sanjangeet.bulletinboard.api.models

data class PostRequest(
    val title: String,
    val content: String,
    val groupId: Int
)

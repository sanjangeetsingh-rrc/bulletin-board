package com.sanjangeet.bulletinboard.api.models.post

data class PostRequest(
    val title: String,
    val content: String,
    val groupId: Int
)

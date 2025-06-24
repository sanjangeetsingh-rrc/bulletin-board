package com.sanjangeet.bulletinboard.api.models.post

data class PostResponse(
    val id: Int,
    val title: String,
    val content: String,
    val groupId: Int,
    val created_at: String,
    val updated_at: String
)

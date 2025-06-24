package com.sanjangeet.bulletinboard.memory.cache

object TokenManager {
    private var accessToken: String? = null

    fun save(token: String) {
        accessToken = token
    }

    fun get(): String? {
        return accessToken
    }
}

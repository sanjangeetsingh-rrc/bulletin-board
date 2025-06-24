package com.sanjangeet.bulletinboard.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.sanjangeet.bulletinboard.api.services.AuthService
import com.sanjangeet.bulletinboard.api.services.UserService
import com.sanjangeet.bulletinboard.api.services.GroupService
import com.sanjangeet.bulletinboard.api.services.PostService
import com.sanjangeet.bulletinboard.api.services.GroupMembershipService

object Client {
    private const val BASE_URL = "http://192.168.56.1:8000/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val groupService: GroupService by lazy {
        retrofit.create(GroupService::class.java)
    }

    val postService: PostService by lazy {
        retrofit.create(PostService::class.java)
    }

    val groupMembershipService: GroupMembershipService by lazy {
        retrofit.create(GroupMembershipService::class.java)
    }
}

package com.sanjangeet.bulletinboard.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.sanjangeet.bulletinboard.api.models.post.PostRequest
import com.sanjangeet.bulletinboard.api.models.post.PostResponse
import retrofit2.http.Header

interface PostService {

    @GET("posts/")
    suspend fun getAllPosts(@Header("Authorization") authHeader: String): List<PostResponse>

    @GET("posts/{id}/")
    suspend fun getPost(@Path("id") id: Int, @Header("Authorization") authHeader: String): PostResponse

    @POST("posts/")
    suspend fun createPost(@Body request: PostRequest, @Header("Authorization") authHeader: String): PostResponse

    @PATCH("posts/{id}/")
    suspend fun updatePost(@Path("id") id: Int, @Body request: PostRequest, @Header("Authorization") authHeader: String): PostResponse

    @DELETE("posts/{id}/")
    suspend fun deletePost(@Path("id") id: Int, @Header("Authorization") authHeader: String): Response<Unit>

    @GET("posts/group_posts/")
    suspend fun getPostsForGroup(@Query("group") groupId: Int, @Header("Authorization") authHeader: String): List<PostResponse>
}

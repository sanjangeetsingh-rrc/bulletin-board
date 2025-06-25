package com.sanjangeet.bulletinboard.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Multipart
import okhttp3.MultipartBody
import retrofit2.http.PATCH
import retrofit2.http.Part
import okhttp3.RequestBody
import com.sanjangeet.bulletinboard.api.models.group.BanMemberRequest
import com.sanjangeet.bulletinboard.api.models.group.CreateGroupRequest
import com.sanjangeet.bulletinboard.api.models.group.GroupResponse
import com.sanjangeet.bulletinboard.api.models.user.UserResponse
import retrofit2.http.Header

interface GroupService {
    @GET("groups/")
    suspend fun getAllGroups(@Header("Authorization") authHeader: String): List<GroupResponse>

    @GET("groups/my/")
    suspend fun getMyGroups(@Header("Authorization") authHeader: String): List<GroupResponse>

    @GET("groups/joined/")
    suspend fun getJoinedGroups(@Header("Authorization") authHeader: String): List<GroupResponse>

    @Multipart
    @POST("groups/")
    suspend fun createGroup(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody?,
        @Part icon: MultipartBody.Part?,
        @Part("whitelist") whitelist: RequestBody?,
        @Part("blacklist") blacklist: RequestBody?,
        @Header("Authorization") authHeader: String
    ): GroupResponse

    @PATCH("groups/{id}/")
    suspend fun updateGroup(@Path("id") id: Int, @Body request: CreateGroupRequest, @Header("Authorization") authHeader: String): GroupResponse

    @DELETE("groups/{id}/")
    suspend fun deleteGroup(@Path("id") id: Int, @Header("Authorization") authHeader: String): Response<Unit>

    @GET("groups/list_members/")
    suspend fun listMembers(@Query("group") groupId: Int, @Header("Authorization") authHeader: String): List<UserResponse>

    @POST("groups/ban_member/")
    suspend fun banMember(@Body request: BanMemberRequest, @Header("Authorization") authHeader: String): Response<Unit>

    @GET("groups/")
    suspend fun searchGroups(@Query("search") query: String, @Header("Authorization") authHeader: String): List<GroupResponse>
}

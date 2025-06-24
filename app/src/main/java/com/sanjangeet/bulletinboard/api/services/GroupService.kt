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
import com.sanjangeet.bulletinboard.api.models.BanMemberRequest
import com.sanjangeet.bulletinboard.api.models.CreateGroupRequest
import com.sanjangeet.bulletinboard.api.models.GroupResponse
import com.sanjangeet.bulletinboard.api.models.UserResponse

interface GroupService {
    @GET("groups/")
    suspend fun getAllGroups(): List<GroupResponse>

    @GET("groups/my/")
    suspend fun getMyGroups(): List<GroupResponse>

    @GET("groups/joined/")
    suspend fun getJoinedGroups(): List<GroupResponse>

    @Multipart
    @POST("groups/")
    suspend fun createGroup(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody?,
        @Part icon: MultipartBody.Part?,
        @Part("whitelist") whitelist: RequestBody?,
        @Part("blacklist") blacklist: RequestBody?
    ): GroupResponse

    @PATCH("groups/{id}/")
    suspend fun updateGroup(@Path("id") id: Int, @Body request: CreateGroupRequest): GroupResponse

    @DELETE("groups/{id}/")
    suspend fun deleteGroup(@Path("id") id: Int): Response<Unit>

    @GET("groups/list_members/")
    suspend fun listMembers(@Query("group") groupId: Int): List<UserResponse>

    @POST("groups/ban_member/")
    suspend fun banMember(@Body request: BanMemberRequest): Response<Unit>

    @GET("groups/")
    suspend fun searchGroups(@Query("search") query: String): List<GroupResponse>
}

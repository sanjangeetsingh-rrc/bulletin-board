package com.sanjangeet.bulletinboard.api.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.sanjangeet.bulletinboard.api.models.GroupMembershipRequest

interface GroupMembershipService {

    @POST("groups/join/")
    suspend fun joinGroup(@Body request: GroupMembershipRequest): Response<Unit>

    @POST("groups/leave/")
    suspend fun leaveGroup(@Body request: GroupMembershipRequest): Response<Unit>
}

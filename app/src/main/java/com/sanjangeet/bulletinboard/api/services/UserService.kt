package com.sanjangeet.bulletinboard.api.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import com.sanjangeet.bulletinboard.api.models.user.UserResponse
import com.sanjangeet.bulletinboard.api.models.user.UserUpdateRequest
import com.sanjangeet.bulletinboard.api.models.user.PasswordResetRequest
import com.sanjangeet.bulletinboard.api.models.user.PasswordChangeRequest

interface UserService {

    @GET("user/info/")
    suspend fun userInfo(): UserResponse

    @POST("user/update/")
    suspend fun updateUserInfo(@Body request: UserUpdateRequest): Response<Unit>

    @POST("reset-password/")
    suspend fun resetPassword(@Body request: PasswordResetRequest): Response<Unit>

    @POST("user/change-password/")
    suspend fun changePassword(@Body request: PasswordChangeRequest): Response<Unit>
}

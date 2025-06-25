package com.sanjangeet.bulletinboard.api.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import com.sanjangeet.bulletinboard.api.models.user.UserResponse
import com.sanjangeet.bulletinboard.api.models.user.UserUpdateRequest
import com.sanjangeet.bulletinboard.api.models.user.PasswordResetRequest
import com.sanjangeet.bulletinboard.api.models.user.PasswordChangeRequest
import retrofit2.http.Header

interface UserService {

    @GET("user/info/")
    suspend fun userInfo(@Header("Authorization") authHeader: String): UserResponse

    @POST("user/update/")
    suspend fun updateUserInfo(@Body request: UserUpdateRequest, @Header("Authorization") authHeader: String): Response<Unit>

    @POST("reset-password/")
    suspend fun resetPassword(@Body request: PasswordResetRequest): Response<Unit>

    @POST("user/change-password/")
    suspend fun changePassword(@Body request: PasswordChangeRequest, @Header("Authorization") authHeader: String): Response<Unit>
}

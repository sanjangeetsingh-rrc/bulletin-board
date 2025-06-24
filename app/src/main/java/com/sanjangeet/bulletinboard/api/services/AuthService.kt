package com.sanjangeet.bulletinboard.api.services

import retrofit2.http.Body
import retrofit2.http.POST
import com.sanjangeet.bulletinboard.api.models.LoginRequest
import com.sanjangeet.bulletinboard.api.models.LoginResponse
import com.sanjangeet.bulletinboard.api.models.LoginRenewRequest
import com.sanjangeet.bulletinboard.api.models.LoginRenewResponse
import com.sanjangeet.bulletinboard.api.models.SignupRequest
import com.sanjangeet.bulletinboard.api.models.SignupRequestOtpRequest
import retrofit2.Response

interface AuthService {
    @POST("login/")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("login/renew/")
    suspend fun login_renew(@Body loginRenewRequest: LoginRenewRequest): LoginRenewResponse

    @POST("signup/")
    suspend fun signup(@Body request: SignupRequest): LoginResponse

    @POST("signup/request-otp/")
    suspend fun requestOtp(@Body request: SignupRequestOtpRequest): Response<Unit>
}

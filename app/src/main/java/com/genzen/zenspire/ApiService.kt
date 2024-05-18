package com.genzen.zenspire

import com.genzen.zenspire.auth.LoginRequest
import com.genzen.zenspire.auth.LoginResponse
import com.genzen.zenspire.auth.RegisterRequest
import com.genzen.zenspire.dashboard.UserDataRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("users/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<Unit>

    @POST("users/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("user_data")
    fun postUserData(
        @Header("Authorization") token: String,
        @Body request: UserDataRequest
    ): Call<Unit>

    @GET("user_data")
    fun getUserData(): Call<UserDataRequest>
}
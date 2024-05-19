package com.genzen.zenspire.data.api

import com.genzen.zenspire.data.models.auth.LoginRequest
import com.genzen.zenspire.data.models.auth.LoginResponse
import com.genzen.zenspire.data.models.auth.RegisterRequest
import com.genzen.zenspire.data.models.dashboard.UserDataRequest
import com.genzen.zenspire.data.models.dashboard.UserDataResponse
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
    fun getUserData(
        @Header("Authorization") token: String
    ): Call<UserDataResponse>
}
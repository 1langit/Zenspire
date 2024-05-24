package com.genzen.zenspire.data.api

import com.genzen.zenspire.data.models.auth.LoginRequest
import com.genzen.zenspire.data.models.auth.LoginResponse
import com.genzen.zenspire.data.models.auth.RegisterRequest
import com.genzen.zenspire.data.models.dashboard.UserDataRequest
import com.genzen.zenspire.data.models.dashboard.UserDataResponse
import com.genzen.zenspire.data.models.questionnaire.QuestionnaireRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // ---------------------------------- Auth
    @POST("users/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<Unit>

    @POST("users/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    // ---------------------------------- User Data
    @POST("user_data")
    fun postUserData(
        @Header("Authorization") token: String,
        @Body request: UserDataRequest
    ): Call<Unit>

    @GET("user_data")
    fun getUserData(
        @Header("Authorization") token: String
    ): Call<UserDataResponse>

    // ---------------------------------- Questionnaire
    @POST("quisioners")
    fun postQuestionnaire(
        @Header("Authorization") token: String,
        @Body request: QuestionnaireRequest
    ): Call<QuestionnaireRequest>

    @GET("quisioners")
    fun getQuestionnaire(
        @Header("Authorization") token: String
    ): Call<QuestionnaireRequest>

    // ---------------------------------- Community discussion
    @GET("disscusions/popular")
    fun getPopularDiscussions(
        @Header("Authorization") token: String
    ): Call<Unit>
}
package com.genzen.zenspire.data.api

import com.genzen.zenspire.data.models.auth.LoginRequest
import com.genzen.zenspire.data.models.auth.LoginResponse
import com.genzen.zenspire.data.models.auth.RegisterRequest
import com.genzen.zenspire.data.models.community.PostList
import com.genzen.zenspire.data.models.community.PostSingle
import com.genzen.zenspire.data.models.community.PostUpload
import com.genzen.zenspire.data.models.dashboard.UserDataRequest
import com.genzen.zenspire.data.models.dashboard.UserDataResponse
import com.genzen.zenspire.data.models.journal.JournalData
import com.genzen.zenspire.data.models.journal.JournalList
import com.genzen.zenspire.data.models.journal.JournalSingle
import com.genzen.zenspire.data.models.questionnaire.QuestionnaireRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    // ---------------------------------- User JournalData
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
    @GET("discussions/{id}")
    suspend fun getDiscussion(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): PostSingle

    @GET("discussions")
    suspend fun getDiscussions(
        @Header("Authorization") token: String
    ): PostList

    @GET("discussions/liked")
    suspend fun getLikedDiscussions(
        @Header("Authorization") token: String
    ): PostList

    @GET("discussions/current")
    suspend fun getCurrentUserDiscussions(
        @Header("Authorization") token: String
    ): PostList

    @POST("discussions")
    suspend fun postDiscussion(
        @Header("Authorization") token: String,
        @Body request: PostUpload
    )

    @POST("discussions/{id}/like")
    suspend fun postLikeDiscussion(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    )

    // ---------------------------------- JournalData
    @GET("journals")
    suspend fun getJournals(
        @Header("Authorization") token: String
    ): JournalList

    @POST("journals")
    suspend fun addJournal(
        @Header("Authorization") token: String,
        @Body request: JournalData
    ): JournalSingle

    @PUT("journals/{id}")
    suspend fun updateJournal(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: JournalData
    ): JournalSingle
}
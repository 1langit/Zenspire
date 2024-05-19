package com.genzen.zenspire.data.models.dashboard

data class Data(
    val birthday: String,
    val created_at: String,
    val gender: String,
    val health_condition: List<String>,
    val id: Int,
    val updated_at: String,
    val user_id: Int
)
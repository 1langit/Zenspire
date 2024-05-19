package com.genzen.zenspire.data.models.auth

data class Data(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String? = null,
    val experience_points: Int,
    val isAnonymous: Boolean
)
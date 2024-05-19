package com.genzen.zenspire.data.models.auth

data class RegisterRequest(
    val email: String,
    val first_name: String,
    val last_name: String? = null,
    val password: String
)
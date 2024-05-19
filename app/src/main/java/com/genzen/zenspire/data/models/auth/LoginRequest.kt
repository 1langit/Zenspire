package com.genzen.zenspire.data.models.auth

data class LoginRequest(
    val email: String,
    val password: String
)
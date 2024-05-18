package com.genzen.zenspire.dashboard

data class UserDataRequest(
    val birthday: String,
    val gender: String,
    val health_condition: List<String>? = null
)
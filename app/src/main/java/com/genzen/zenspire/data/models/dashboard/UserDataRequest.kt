package com.genzen.zenspire.data.models.dashboard

data class UserDataRequest(
    val birthday: String,
    val gender: String,
    val health_condition: List<String>? = null
)
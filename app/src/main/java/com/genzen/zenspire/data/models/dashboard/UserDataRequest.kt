package com.genzen.zenspire.data.models.dashboard

data class UserDataRequest(
    val gender: String,
    val birthday: String,
    val preferences: List<String>? = null
)
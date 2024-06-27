package com.genzen.zenspire.data.models.community

data class Discussion(
    val _count: Count,
    val body: String,
    val category: List<String>,
    val created_at: String,
    val id: Int,
    val image: String,
    val title: String,
    val updated_at: String,
    val user: User,
    val user_id: Int
)
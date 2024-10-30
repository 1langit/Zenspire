package com.genzen.zenspire.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostData(
    val id: Int,
    val user_id: Int,
    val title: String,
    val category: List<String>,
    val body: String,
    val image: String?,
    val isLiked: Boolean,
    val created_at: String,
    val updated_at: String,
    val _count: Count,
    val user: User,
    val comment: List<Comment>?
): Parcelable
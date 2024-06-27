package com.genzen.zenspire.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: String,
    val title: String,
    val author: String,
    val timestamp: String,
    val categories: List<String>,
    val likesCount: Int,
    val commentCount: Int,
    val content: String
): Parcelable

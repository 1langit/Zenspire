package com.genzen.zenspire.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: Int,
    val user_id: Int,
    val discussion_id: Int,
    val body: String,
    val created_at: String,
    val updated_at: String,
    val user: User
): Parcelable
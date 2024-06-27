package com.genzen.zenspire.data.models.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val title: String,
    val author: String,
    val clinic: String,
    val timestamp: String,
    val categories: List<String>,
    val content: String
): Parcelable

package com.genzen.zenspire.data.models.journal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Journal(
    val id: Int,
    val user_id: Int,
    val title: String,
    val mood: String,
    val question_1: String,
    val question_2: String,
    val question_3: String,
    val question_4: String,
    val created_at: String,
    val updated_at: String
): Parcelable
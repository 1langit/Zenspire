package com.genzen.zenspire.data.models.journal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JournalData(
    val id: Int? = null,
    val user_id: Int? = null,
    val title: String,
    val mood: String,
    val question_1: String,
    val question_2: String,
    val question_3: String,
    val question_4: String,
    val created_at: String? = null,
    val updated_at: String? = null
): Parcelable
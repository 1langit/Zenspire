package com.genzen.zenspire.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val first_name: String,
    val last_name: String?,
    val isAnonymous: Boolean
): Parcelable
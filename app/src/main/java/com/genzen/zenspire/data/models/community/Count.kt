package com.genzen.zenspire.data.models.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Count(
    val comment: Int,
    val discussionLike: Int
): Parcelable
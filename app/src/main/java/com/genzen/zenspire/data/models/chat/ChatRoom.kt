package com.genzen.zenspire.data.models.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ChatRoom(
    var id: String = UUID.randomUUID().toString(),
    var users: List<Contact> = emptyList(),
    var lastMessage: String = "",
    var timestamp: Long = 0,
    var unreadCount: Int = 0
): Parcelable

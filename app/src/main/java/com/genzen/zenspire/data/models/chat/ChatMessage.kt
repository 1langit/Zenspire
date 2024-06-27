package com.genzen.zenspire.data.models.chat

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var senderId: String = "",
    var text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
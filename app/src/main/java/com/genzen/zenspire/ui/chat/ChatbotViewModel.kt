package com.genzen.zenspire.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.chat.ChatMessage

object ChatbotViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        addMessage(ChatMessage(senderId = "1", text = "Selamat datang di sesi terapi Cognitive Behavorial Therapy. Saya Zenia, konselor AI Anda untuk terapi Cognitive Behavioral. Jika ada yang ingin Anda ceritakan, jangan ragu untuk berbagi"))
    }

    fun addMessage(message: ChatMessage) {
        _isLoading.value = message.senderId != "1"
        _messages.value = _messages.value.orEmpty() + message
    }

    fun clearChatHistory() {
        _messages.value = emptyList()
    }
}
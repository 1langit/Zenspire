package com.genzen.zenspire.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.data.models.chat.ChatRoom
import com.genzen.zenspire.data.models.chat.Contact
import com.genzen.zenspire.data.seeder.ContactSeed

object ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    private val _history = MutableLiveData<List<ChatRoom>>()
    val history: LiveData<List<ChatRoom>> get() = _history

    init {
        val seeder = ContactSeed()
        _contacts.value = seeder.getContacts()
    }

    fun initiateChatroom(chatroom: ChatRoom) {
        if (chatroom !in _history.value.orEmpty()) {
            _history.value = listOf(chatroom) + _history.value.orEmpty()
        }
    }
}
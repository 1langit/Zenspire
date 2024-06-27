package com.genzen.zenspire.data.repositories

import android.util.Log
import com.genzen.zenspire.data.firestore.FirestoreClient
import com.genzen.zenspire.data.models.chat.ChatMessage
import com.genzen.zenspire.data.models.chat.ChatRoom
import com.genzen.zenspire.data.models.chat.Contact
import com.google.firebase.firestore.FirebaseFirestore

class ChatRepository {

    private val firestoreInstance: FirebaseFirestore
        get() = FirestoreClient.firestoreInstance

    fun sendMessage(message: ChatMessage, chatRoom: ChatRoom) {
        val chatRoomRef = firestoreInstance.collection("chatrooms").document(chatRoom.id)

        firestoreInstance.runTransaction { transaction ->
            val messageRef = chatRoomRef.collection("messages").document(message.id)
            transaction.set(messageRef, message)

            transaction.update(chatRoomRef, mapOf(
                "lastMessage" to message.text,
                "time" to message.timestamp
            ))
        }
    }

    fun createChatRoom(chatRoom: ChatRoom) {
        firestoreInstance.collection("chatrooms")
            .document(chatRoom.id)
            .set(chatRoom)
    }

    fun observeMessages(chatRoom: ChatRoom, onMessagesChanged: (List<ChatMessage>) -> Unit) {
        firestoreInstance.collection("chatrooms")
            .document(chatRoom.id)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val messages = snapshot.toObjects(ChatMessage::class.java)
                    onMessagesChanged(messages)
                } else {
                    onMessagesChanged(emptyList())
                }
            }
    }

    fun getChatRoomsForCurrentUser(currentUser: Contact, onChatRoomsRetrieved: (List<ChatRoom>) -> Unit) {
        firestoreInstance.collection("chatrooms")
            .whereArrayContains("users", currentUser)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val chatRooms = snapshot.toObjects(ChatRoom::class.java)
                    onChatRoomsRetrieved(chatRooms)
                } else {
                    onChatRoomsRetrieved(emptyList())
                }
            }
    }
}
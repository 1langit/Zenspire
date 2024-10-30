package com.genzen.zenspire.ui.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.BuildConfig
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.chat.ChatMessage
import com.genzen.zenspire.data.models.chat.ChatRoom
import com.genzen.zenspire.data.repositories.ChatRepository
import com.genzen.zenspire.databinding.ActivityChatBinding
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val chatbotViewModel: ChatbotViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chat: Chat
    private lateinit var chatRepository: ChatRepository
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = PrefManager.getInstance(this@ChatActivity)
        chatAdapter = ChatAdapter(this@ChatActivity)
        chatRepository = ChatRepository()

        val chatroom: ChatRoom? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("CHATROOM", ChatRoom::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("CHATROOM") as? ChatRoom
        }

        with(binding) {
            if (chatroom != null) {
                initializeUserChat(chatroom)
                topAppBar.title = chatroom.users.find { it.id != prefManager.getUid().toString() }!!.name
                topAppBar.menu.findItem(R.id.end_chat).isVisible = false
            } else {
                initializeChatbot()
                topAppBar.title = "Chat Zenia"
            }

            topAppBar.apply {
                setNavigationOnClickListener {
                    finish()
                }
                menu.findItem(R.id.end_chat).setOnMenuItemClickListener {
                    endChatDialog()
                    true
                }
            }

            rvChat.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = chatAdapter
            }

            btnSend.setOnClickListener {
                val message = edtMessage.text.toString()
                if (message.isNotEmpty() && !chatbotViewModel.isLoading.value!!) {
                    if (chatroom != null) sendToOtherUser(message, chatroom) else sendToModel(message)
                    edtMessage.setText("")
                }
            }
        }
    }

    private fun initializeUserChat(chatroom: ChatRoom) {
        chatRepository.createChatRoom(chatroom)
        chatRepository.observeMessages(chatroom) { messages ->
            chatAdapter.setMessages(messages)
            binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    private fun initializeChatbot() {
        val generativeModel = GenerativeModel(
            apiKey = BuildConfig.GEMINI_API_KEY,
            modelName = "gemini-1.5-flash",
        )

        chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text(getString(R.string.chat_prompt)) },
                content(role = "model") { text(getString(R.string.first_chat)) }
            )
        )

        chatbotViewModel.messages.observe(this@ChatActivity) { messages ->
            chatAdapter.setMessages(messages)
            binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    private fun sendToOtherUser(message: String, chatroom: ChatRoom) {
        chatRepository.sendMessage(
            ChatMessage(senderId = prefManager.getUid().toString(), text = message),
            chatroom
        )
    }

    private fun sendToModel(message: String) {
        chatbotViewModel.addMessage(ChatMessage(senderId = prefManager.getUid().toString(), text = message))
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = chat.sendMessage(message)
            response.text?.let { modelResponse ->
                withContext(Dispatchers.Main) {
                    chatbotViewModel.addMessage(ChatMessage(senderId = "1", text = modelResponse))
                }
            }
        }
    }

    private fun endChatDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Akhiri percakapan?")
            .setMessage("Anda akan mendapatksn analisis percakapan")
            .setPositiveButton("Akhiri") { dialog, _ ->
                dialog.dismiss()
                chatAnalysisDialog()
            }.setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun chatAnalysisDialog() {
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val customView: View = inflater.inflate(R.layout.dialog_analysis, null)
        customView.findViewById<TextView>(R.id.txt_title).text = "Hasil Chat"

        MaterialAlertDialogBuilder(this)
            .setView(customView)
            .setPositiveButton("Tutup") { dialog, _ ->
                dialog.dismiss()
            }.create().show()

        chatAdapter.resetMessages()
        chatbotViewModel.clearChatHistory()
        chatbotViewModel.addMessage(ChatMessage(senderId = "1", text = getString(R.string.first_chat)))
    }
}
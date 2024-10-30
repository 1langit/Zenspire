package com.genzen.zenspire.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.chat.Contact
import com.genzen.zenspire.data.repositories.ChatRepository
import com.genzen.zenspire.databinding.ActivityChatHistoryBinding

class ChatHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatHistoryBinding
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private lateinit var chatRepository: ChatRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefManager = PrefManager.getInstance(this@ChatHistoryActivity)
        chatRepository = ChatRepository()

        chatRoomAdapter = ChatRoomAdapter(this@ChatHistoryActivity) { chatroom ->
            val newIntent = Intent(this@ChatHistoryActivity, ChatActivity::class.java)
            newIntent.putExtra("CHATROOM", chatroom)
            startActivity(newIntent)
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            rvContact.apply {
                layoutManager = LinearLayoutManager(this@ChatHistoryActivity)
                adapter = chatRoomAdapter
            }

            fab.setOnClickListener {
                val newIntent = Intent(this@ChatHistoryActivity, FindPsychologistActivity::class.java)
                startActivity(newIntent)
            }

            chatRepository.getChatRoomsForCurrentUser(
                Contact(prefManager.getUid().toString(),"${prefManager.getFirstName()} ${prefManager.getLastName()}", "Pengguna")
            ) { messages ->
                chatRoomAdapter.setChatRooms(messages)
                txtEmpty.visibility = if (messages.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}
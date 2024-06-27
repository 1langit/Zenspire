package com.genzen.zenspire.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.chat.ChatRoom
import com.genzen.zenspire.data.models.chat.Contact
import com.genzen.zenspire.databinding.ActivityFindPsychologistBinding
import java.util.UUID

class FindPsychologistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPsychologistBinding
    private val contactViewModel: ContactViewModel by viewModels()
    private val contactAdapter = ContactAdapter { contact ->
        val newIntent = Intent(this@FindPsychologistActivity, ChatActivity::class.java)
        val prefManager = PrefManager.getInstance(this@FindPsychologistActivity)
        val chatroom = ChatRoom(
            UUID.randomUUID().toString(),
            listOf(contact, Contact(prefManager.getUid().toString(),"${prefManager.getFirstName()} ${prefManager.getLastName()}", "Pengguna")),
            "",
            0,
            0
        )
        newIntent.putExtra("CHATROOM", chatroom)
        startActivity(newIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFindPsychologistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            rvContact.apply {
                layoutManager = LinearLayoutManager(this@FindPsychologistActivity)
                adapter = contactAdapter
            }

            contactViewModel.contacts.observe(this@FindPsychologistActivity) { contacts ->
                contactAdapter.setContacts(contacts)
            }
        }
    }
}
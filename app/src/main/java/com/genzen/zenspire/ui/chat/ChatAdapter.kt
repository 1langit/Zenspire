package com.genzen.zenspire.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.chat.ChatMessage
import com.genzen.zenspire.databinding.ComponentChatLeftBinding
import com.genzen.zenspire.databinding.ComponentChatRightBinding
import io.noties.markwon.Markwon

class ChatAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val chatList = ArrayList<ChatMessage>()
    private val prefManager = PrefManager.getInstance(context)

    companion object {
        const val RIGHT_VIEW = 1
        const val LEFT_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            LEFT_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ComponentChatLeftBinding.inflate(inflater, parent, false)
                LeftViewHolder(binding)
            }
            RIGHT_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ComponentChatRightBinding.inflate(inflater, parent, false)
                RightViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is LeftViewHolder -> holder.bind(chatList[position])
            is RightViewHolder -> holder.bind(chatList[position])
            else -> throw IllegalArgumentException("Invalid view holder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].senderId == prefManager.getUid().toString()) RIGHT_VIEW else LEFT_VIEW
    }

    inner class LeftViewHolder(private val binding: ComponentChatLeftBinding) : ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                Markwon.create(context).setMarkdown(txtMessage, chat.text)
                if (chat.senderId != "1") imgChatbot.visibility = View.GONE
            }
        }
    }

    inner class RightViewHolder(private val binding: ComponentChatRightBinding) : ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                txtMessage.text = chat.text
            }
        }
    }

    fun setMessages(messages: List<ChatMessage>) {
        chatList.clear()
        chatList.addAll(messages)
        notifyItemChanged(chatList.size - 1)
    }

    fun resetMessages() {
        chatList.clear()
        notifyDataSetChanged()
    }
}
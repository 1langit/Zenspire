package com.genzen.zenspire.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.chat.ChatRoom
import com.genzen.zenspire.databinding.ComponentContactBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatRoomAdapter(
    context: Context,
    private val onClick: (ChatRoom) -> Unit
) : RecyclerView.Adapter<ChatRoomAdapter.ItemContactViewHolder>() {

    private val prefManager = PrefManager.getInstance(context)
    private val chatroomList = ArrayList<ChatRoom>()

    inner class ItemContactViewHolder(private val binding: ComponentContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatroom: ChatRoom) {
            with(binding) {
                txtName.text = chatroom.users.find { it.id != prefManager.getUid().toString() }!!.name
                txtPreview.text = chatroom.lastMessage
                txtUnread.text = chatroom.unreadCount.toString()
                txtUnread.visibility = if (chatroom.unreadCount > 0) View.VISIBLE else View.GONE

                val date = Date(chatroom.timestamp)
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                txtTime.text = dateFormat.format(date)
            }

            itemView.setOnClickListener {
                onClick(chatroom)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomAdapter.ItemContactViewHolder {
        val binding = ComponentContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatroomList.size
    }

    override fun onBindViewHolder(holder: ChatRoomAdapter.ItemContactViewHolder, position: Int) {
        holder.bind(chatroomList[position])
    }

    fun setChatRooms(chatroom: List<ChatRoom>) {
        chatroomList.clear()
        chatroomList.addAll(chatroom)
        notifyDataSetChanged()
    }
}
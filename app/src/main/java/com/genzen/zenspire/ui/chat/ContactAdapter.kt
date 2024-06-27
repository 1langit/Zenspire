package com.genzen.zenspire.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genzen.zenspire.data.models.chat.Contact
import com.genzen.zenspire.databinding.ComponentContactBinding

class ContactAdapter(private val onClick: (Contact) -> Unit) : RecyclerView.Adapter<ContactAdapter.ItemContactViewHolder>() {

    private val contactList = ArrayList<Contact>()

    inner class ItemContactViewHolder(private val binding: ComponentContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                txtName.text = contact.name
                txtPreview.text = contact.information
                txtTime.visibility = View.GONE
                txtUnread.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ItemContactViewHolder {
        val binding = ComponentContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ItemContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    fun setContacts(contacts: List<Contact>) {
        contactList.clear()
        contactList.addAll(contacts)
        notifyDataSetChanged()
    }
}
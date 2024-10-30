package com.genzen.zenspire.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genzen.zenspire.data.models.community.Comment
import com.genzen.zenspire.databinding.ComponentCommentBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ItemCommentViewHolder>() {

    private val commentlist = ArrayList<Comment>()

    inner class ItemCommentViewHolder(private val binding: ComponentCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            with(binding) {
                txtName.text = "${comment.user.first_name} ${comment.user.last_name?: ' '}"
                txtTimestamp.text = comment.created_at
                txtComment.text = comment.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentViewHolder {
        val binding = ComponentCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: CommentAdapter.ItemCommentViewHolder, position: Int) {
        holder.bind(commentlist[position])
    }

    fun setComments(comments: List<Comment>) {
        commentlist.clear()
        commentlist.addAll(comments)
        notifyDataSetChanged()
    }
}
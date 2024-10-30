package com.genzen.zenspire.ui.community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.community.PostData
import com.genzen.zenspire.databinding.ComponentCommunityBinding
import com.google.android.material.chip.Chip

class PostAdapter(
    private val context: Context,
    private val onClick: (PostData) -> Unit
) : RecyclerView.Adapter<PostAdapter.ItemPostViewHolder>() {

    private val postList = ArrayList<PostData>()

    inner class ItemPostViewHolder(private val binding: ComponentCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostData) {
            with(binding) {
                txtName.text = "${post.user.first_name} ${post.user.last_name?: ' '}"
                txtTimestamp.text = post.created_at
                txtTitle.text = post.title
                txtContent.text = post.body
                txtLike.text = "${post._count.discussionLike} suka"
                btnComment.text = "${post._count.comment} komentar"
                if (post.isLiked) btnLike.background.setTint(ContextCompat.getColor(context, R.color.primary_blue_100))

                chipCategory.removeAllViews()
                post.category.forEach {
                    val chip = Chip(itemView.context, null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.primary_blue_50)
                    }
                    chipCategory.addView(chip)
                }
            }

            itemView.setOnClickListener {
                onClick(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ItemPostViewHolder {
        val binding = ComponentCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostAdapter.ItemPostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    fun setPosts(posts: List<PostData>) {
        postList.clear()
        postList.addAll(posts)
        notifyDataSetChanged()
    }
}
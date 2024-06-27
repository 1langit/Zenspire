package com.genzen.zenspire.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.community.Post
import com.genzen.zenspire.databinding.ComponentArticleBinding
import com.genzen.zenspire.databinding.ComponentCommunityBinding
import com.google.android.material.chip.Chip

class PostAdapter(private val onClick: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.ItemPostViewHolder>() {

    private val postList = ArrayList<Post>()

    inner class ItemPostViewHolder(private val binding: ComponentCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            with(binding) {
                txtName.text = post.author
                txtTimestamp.text = post.timestamp
                txtTitle.text = post.title
                txtContent.text = post.content
                btnLike.text = "${post.likesCount.toString()} suka"
                btnComment.text = "${post.commentCount.toString()} komentar"

                chipCategory.removeAllViews()
                post.categories.forEach {
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

    fun setPosts(posts: List<Post>) {
        postList.clear()
        postList.addAll(posts)
        notifyDataSetChanged()
    }
}
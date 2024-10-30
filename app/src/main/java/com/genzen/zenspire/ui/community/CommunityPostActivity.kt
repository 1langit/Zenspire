package com.genzen.zenspire.ui.community

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.community.Comment
import com.genzen.zenspire.data.models.community.PostData
import com.genzen.zenspire.databinding.ActivityCommunityPostBinding
import com.genzen.zenspire.databinding.SheetCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommunityPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityPostBinding
    private val communityViewModel by viewModels<CommunityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        communityViewModel.setToken(PrefManager.getInstance(this).getToken())

        var post: PostData? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("POST", PostData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("POST") as? PostData
        }

        with(binding) {
            post?.let { postData ->
                var likeByUser = postData.isLiked

                txtName.text = "${postData.user.first_name} ${postData.user.last_name?: ' '}"
                txtTimestamp.text = postData.created_at
                txtTitle.text = postData.title
                txtContent.text = postData.body
                txtLike.text = "${postData._count.discussionLike} suka"
                btnComment.text = "${postData._count.comment} komentar"
                if (likeByUser) btnLike.background.setTint(getColor(R.color.primary_blue_100))

                chipCategory.removeAllViews()
                postData.category.forEach {
                    val chip = Chip(this@CommunityPostActivity, null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.primary_blue_50)
                    }
                    chipCategory.addView(chip)
                }

                btnLike.setOnClickListener {
                    val count = txtLike.text.split(" ")[0].toInt()
                    if (!likeByUser) {
                        txtLike.text = "${count + 1} suka"
                        btnLike.background.setTint(getColor(R.color.primary_blue_100))
                    } else {
                        txtLike.text = "${count - 1} suka"
                        btnLike.background.setTint(getColor(R.color.transparent))
                    }
                    likeByUser = !likeByUser
                    communityViewModel.likePost(postData.id)
                    setResult(RESULT_OK)
                }

                btnComment.setOnClickListener {
                    if (postData.comment.isNullOrEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            post = communityViewModel.fetchPost(postData.id)
                            withContext(Dispatchers.Main) {
                                showCommentSheet(post!!.comment)
                            }
                        }
                    } else {
                        showCommentSheet(post!!.comment)
                    }
                }
            }

            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun showCommentSheet(comment: List<Comment>?) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding = SheetCommentBinding.inflate(layoutInflater)
        val commentAdapter = CommentAdapter()

        with(bottomSheetBinding) {
            if (comment != null) {
                commentAdapter.setComments(comment)
                rvComment.apply {
                    layoutManager = LinearLayoutManager(this@CommunityPostActivity)
                    adapter = commentAdapter
                }
            } else {
                txtEmpty.visibility = View.VISIBLE
                rvComment.visibility = View.GONE
            }
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }
}
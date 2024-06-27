package com.genzen.zenspire.ui.community

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.community.Post
import com.genzen.zenspire.databinding.ActivityCommunityUploadBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CommunityUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityUploadBinding
    private val communityViewModel: CommunityViewModel by viewModels()
    private var selectedCategories = BooleanArray(7)
    private lateinit var categories: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCommunityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefManager = PrefManager.getInstance(this@CommunityUploadActivity)
        categories = resources.getStringArray(R.array.community_post_category)

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnCategory.setOnClickListener {
                showCategoryDialog()
            }

            btnCancel.setOnClickListener {
                finish()
            }

            btnUpload.setOnClickListener {
                val title = edtTitle.text.toString()
                val content = edtContent.text.toString()
                val fixedCategories = ArrayList<String>()
                for (item in selectedCategories) {
                    if (item) {
                        fixedCategories.add(categories[selectedCategories.indexOf(item)])
                    }
                }

                if (title.isNotBlank() && content.isNotBlank()) {
                    communityViewModel.uploadPost(
                        Post(
                            "0",
                            title,
                            "${prefManager.getFirstName()} ${prefManager.getLastName()}",
                            "Baru saja",
                            listOf(),
                            0,
                            0,
                            content
                        )
                    )
                    finish()
                }
            }
        }
    }

    private fun showCategoryDialog() {
        val currentCategories = selectedCategories

        MaterialAlertDialogBuilder(this)
            .setTitle("Pilih Kategori")
            .setMultiChoiceItems(categories, currentCategories) { dialog, which, isChecked ->
                currentCategories[which] = isChecked
            }
            .setPositiveButton("Pilih") { dialog, which ->
                selectedCategories = currentCategories
                binding.chipCategory.removeAllViews()
                categories.forEachIndexed { index, category ->
                    if (selectedCategories[index]) {
                        val chip = Chip(this, null, R.style.ChipCategory).apply {
                            text = category
                            setChipBackgroundColorResource(R.color.primary_blue_50)
                        }
                        binding.chipCategory.addView(chip)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Tutup") { dialog, which ->
                dialog.dismiss()
            }.show()
    }
}
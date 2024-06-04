package com.genzen.zenspire.ui.article

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityArticleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnFilter.setOnClickListener {
                showCategoryDialog()
            }
        }
    }

    private fun showCategoryDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Pilih Kategori")
            .setMultiChoiceItems(
                resources.getStringArray(R.array.community_post_category
                ), null) { dialog, which, isChecked ->

            }.setPositiveButton("Pilih") { dialog, which ->
                dialog.dismiss()
            }.setNegativeButton("Tutup") { dialog, which ->
                dialog.dismiss()
            }.show()
    }
}
package com.genzen.zenspire.ui.article

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityArticleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private val articleViewModel: ArticleViewModel by viewModels()
    private val articleAdapter = ArticleAdapter { article ->
        val newIntent = Intent(this@ArticleActivity, ArticleReadActivity::class.java)
        newIntent.putExtra("ARTICLE", article)
        startActivity(newIntent)
    }

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

            rvArticle.apply {
                layoutManager = LinearLayoutManager(this@ArticleActivity)
                adapter = articleAdapter
            }

            articleViewModel.article.observe(this@ArticleActivity) { articles ->
                articleAdapter.setArticles(articles)
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
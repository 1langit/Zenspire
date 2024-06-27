package com.genzen.zenspire.ui.article

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.article.Article
import com.genzen.zenspire.databinding.ActivityArticleReadBinding
import com.google.android.material.chip.Chip

class ArticleReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val article: Article? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("ARTICLE", Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("ARTICLE") as? Article
        }

        with(binding) {
            article?.let {
                txtName.text = it.author
                txtClinicTimestamp.text = "${it.clinic}  •  ${it.timestamp}"
                txtTitle.text = it.title
                txtContent.text = it.content

                chipCategory.removeAllViews()
                for (item in it.categories) {
                    val chip = Chip(this@ArticleReadActivity)
                    chip.text = item
                    chip.isClickable = false
                    chipCategory.addView(chip)
                }
            }

            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}
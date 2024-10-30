package com.genzen.zenspire.ui.article

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.article.Article
import com.genzen.zenspire.data.seeder.ArticleSeed
import com.genzen.zenspire.databinding.ActivityArticleReadBinding
import com.google.android.material.chip.Chip

class ArticleReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                article.categories.forEach {
                    val chip = Chip(this@ArticleReadActivity, null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.primary_blue_50)
                    }
                    chipCategory.addView(chip)
                }
            }

            articleOther.apply {
                val otherArticle = ArticleSeed().getArticle((0..9).random())

                txtName.text = otherArticle.author
                txtClinicTimestamp.text = "${otherArticle.clinic}  •  ${otherArticle.timestamp}"
                txtTitle.text = otherArticle.title
                txtContent.text = otherArticle.content

                chipCategory.removeAllViews()
                otherArticle.categories.forEach {
                    val chip = Chip(this@ArticleReadActivity, null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.primary_blue_50)
                    }
                    chipCategory.addView(chip)
                }

                root.setOnClickListener {
                    val newIntent = Intent(this@ArticleReadActivity, ArticleReadActivity::class.java)
                    newIntent.putExtra("ARTICLE", otherArticle)
                    startActivity(newIntent)
                }
            }

            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}
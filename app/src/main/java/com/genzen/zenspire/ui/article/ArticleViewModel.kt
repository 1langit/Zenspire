package com.genzen.zenspire.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.data.models.article.Article
import com.genzen.zenspire.data.seeder.ArticleSeed

object ArticleViewModel : ViewModel() {

    private var originalArticles: List<Article> = listOf()
    private val _articles = MutableLiveData<List<Article>>()
    val article: LiveData<List<Article>> get() = _articles

    init {
        val seeder = ArticleSeed()
        originalArticles = seeder.getArticles()
        _articles.value = originalArticles
    }

    fun filterAndSearchArticles(query: String, selectedCategories: List<String>) {
        _articles.value = originalArticles
            .filter { article ->
                selectedCategories.isEmpty() || article.categories.any { category -> selectedCategories.contains(category) }
            }
            .filter { article ->
                article.title.contains(query, ignoreCase = true) ||
                        article.content.contains(query, ignoreCase = true)
            }
    }

    fun fetchArticles() {

    }
}
package com.genzen.zenspire.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.data.models.article.Article
import com.genzen.zenspire.data.seeder.ArticleSeed

object ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val article: LiveData<List<Article>> get() = _articles

    init {
        val seeder = ArticleSeed()
        _articles.value = seeder.getArticles()
    }
}
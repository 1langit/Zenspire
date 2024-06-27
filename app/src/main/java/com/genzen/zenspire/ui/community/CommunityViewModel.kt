package com.genzen.zenspire.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.data.models.community.Post
import com.genzen.zenspire.data.seeder.CommunitySeed

object CommunityViewModel : ViewModel() {

    private val _feedPosts = MutableLiveData<List<Post>>()
    val feedPosts: LiveData<List<Post>> get() = _feedPosts

    private val _likedPosts = MutableLiveData<List<Post>>()
    val likedPosts: LiveData<List<Post>> get() = _likedPosts

    private val _myPosts = MutableLiveData<List<Post>>()
    val myPosts: LiveData<List<Post>> get() = _myPosts

    init {
        val seeder = CommunitySeed()
        _feedPosts.value = seeder.getArticles()
        _likedPosts.value = listOf(seeder.getArticle(6))
    }

    fun uploadPost(post: Post) {
        _myPosts.value = listOf(post) + _myPosts.value.orEmpty()
    }
}
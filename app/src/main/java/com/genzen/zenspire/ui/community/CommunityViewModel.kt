package com.genzen.zenspire.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genzen.zenspire.data.api.ApiClient
import com.genzen.zenspire.data.models.community.PostData
import com.genzen.zenspire.data.models.community.PostUpload
import kotlinx.coroutines.launch

class CommunityViewModel : ViewModel() {

    private val _feedPosts = MutableLiveData<List<PostData>>()
    val feedPosts: LiveData<List<PostData>> get() = _feedPosts

    private val _likedPosts = MutableLiveData<List<PostData>>()
    val likedPosts: LiveData<List<PostData>> get() = _likedPosts

    private val _myPosts = MutableLiveData<List<PostData>>()
    val myPosts: LiveData<List<PostData>> get() = _myPosts

    private val api = ApiClient.getApiInstance()
    private var token = "----"

    fun setToken(token: String) {
        this.token = token
    }

    fun uploadPost(post: PostUpload) {
        viewModelScope.launch {
            api.postDiscussion("Bearer $token", post)
        }
    }

    fun likePost(id: Int) {
        viewModelScope.launch {
            api.postLikeDiscussion(id, "Bearer $token")
        }

    }

    fun fetchPosts() {
        viewModelScope.launch {
            val feedResponse = api.getDiscussions("Bearer $token")
            _feedPosts.postValue(feedResponse.data)

            val likedResponse = api.getLikedDiscussions("Bearer $token")
            _likedPosts.postValue(likedResponse.data)

            val mineResponse = api.getCurrentUserDiscussions("Bearer $token")
            _myPosts.postValue(mineResponse.data)
        }
    }

    suspend fun fetchPost(id: Int) : PostData {
        val response = api.getDiscussion(id, "Bearer $token")
        return response.data
    }
}
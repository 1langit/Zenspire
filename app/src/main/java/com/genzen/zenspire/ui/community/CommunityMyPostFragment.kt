package com.genzen.zenspire.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentCommunityMyPostBinding

class CommunityMyPostFragment : Fragment() {

    private lateinit var binding: FragmentCommunityMyPostBinding
    private val communityViewModel: CommunityViewModel by viewModels()
    private val postAdapter = PostAdapter { post ->
        val newIntent = Intent(requireContext(), CommunityPostActivity::class.java)
        newIntent.putExtra("POST", post)
        startActivity(newIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityMyPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvCommunity.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postAdapter
            }

            communityViewModel.myPosts.observe(viewLifecycleOwner) { posts ->
                postAdapter.setPosts(posts)
            }
        }
    }
}
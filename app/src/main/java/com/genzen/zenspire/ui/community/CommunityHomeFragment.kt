package com.genzen.zenspire.ui.community

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentCommunityHomeBinding
import com.genzen.zenspire.ui.dashboard.CommunityFragment

class CommunityHomeFragment : Fragment() {

    private lateinit var binding: FragmentCommunityHomeBinding
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
        binding = FragmentCommunityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnPost.setOnClickListener {
                val newIntent = Intent(requireContext(), CommunityUploadActivity::class.java)
                startActivity(newIntent)
            }

            rvCommunity.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postAdapter
            }

            communityViewModel.feedPosts.observe(viewLifecycleOwner) { posts ->
                postAdapter.setPosts(posts)
            }
        }
    }
}
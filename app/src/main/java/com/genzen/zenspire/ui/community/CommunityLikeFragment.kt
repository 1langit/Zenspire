package com.genzen.zenspire.ui.community

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.databinding.FragmentCommunityLikeBinding

class CommunityLikeFragment : Fragment() {

    private lateinit var binding: FragmentCommunityLikeBinding
    private lateinit var postAdapter: PostAdapter
    private val communityViewModel by activityViewModels<CommunityViewModel>()
    private val activityContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            communityViewModel.fetchPosts()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityLikeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostAdapter(requireContext()) { post ->
            val newIntent = Intent(requireContext(), CommunityPostActivity::class.java)
            newIntent.putExtra("POST", post)
            activityContract.launch(newIntent)
        }

        with(binding) {
            rvCommunity.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postAdapter
            }

            communityViewModel.likedPosts.observe(viewLifecycleOwner) { posts ->
                postAdapter.setPosts(posts)
            }
        }
    }
}
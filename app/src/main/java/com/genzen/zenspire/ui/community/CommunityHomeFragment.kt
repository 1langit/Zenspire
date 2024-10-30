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
import com.genzen.zenspire.databinding.FragmentCommunityHomeBinding
import com.genzen.zenspire.ui.dashboard.CommunityFragment

class CommunityHomeFragment : Fragment() {

    private lateinit var binding: FragmentCommunityHomeBinding
    private lateinit var postAdapter: PostAdapter
    private val communityViewModel by activityViewModels<CommunityViewModel>()
    private val activityContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            communityViewModel.fetchPosts()
        } else if (it.resultCode == Activity.RESULT_FIRST_USER) {
            val communityFragment = requireParentFragment() as CommunityFragment
            communityFragment.navigateToPage(2)
            communityViewModel.fetchPosts()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityHomeBinding.inflate(layoutInflater)
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
            btnPost.setOnClickListener {
                val newIntent = Intent(requireContext(), CommunityUploadActivity::class.java)
                activityContract.launch(newIntent)
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
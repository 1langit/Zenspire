package com.genzen.zenspire.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentCommunityHomeBinding

class CommunityHomeFragment : Fragment() {

    private lateinit var binding: FragmentCommunityHomeBinding

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
        }
    }
}
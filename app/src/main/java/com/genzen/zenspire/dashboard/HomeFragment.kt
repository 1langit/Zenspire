package com.genzen.zenspire.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.PrefManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        prefManager = PrefManager.getInstance(requireContext())

        with(binding) {
            txtName.text = prefManager.getFirstName()
        }

        return binding.root
    }
}
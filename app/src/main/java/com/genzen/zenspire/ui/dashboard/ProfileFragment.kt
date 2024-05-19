package com.genzen.zenspire.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.ui.auth.LoginActivity
import com.genzen.zenspire.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager.getInstance(requireContext())
        binding.btnLogout.setOnClickListener {
            prefManager.clear()
            val newIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(newIntent)
            activity?.finish()
        }
    }
}
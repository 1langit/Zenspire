package com.genzen.zenspire.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.PrefManager
import com.genzen.zenspire.R
import com.genzen.zenspire.auth.LoginActivity
import com.genzen.zenspire.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.btnLogout.setOnClickListener {
            logout()
            val newIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(newIntent)
            activity?.finish()
        }

        return binding.root
    }

    private fun logout() {
        val prefManager = PrefManager.getInstance(requireContext())
        prefManager.clear()
    }
}
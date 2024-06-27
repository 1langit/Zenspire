package com.genzen.zenspire.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.ui.auth.LoginActivity
import com.genzen.zenspire.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        with(binding) {
            txtName.text = "${prefManager.getFirstName()} ${prefManager.getLastName()}"
            txtEmail.text = prefManager.getEmail()
            btnLogout.setOnClickListener {
                showLogoutDialog(prefManager)
            }
        }
    }

    private fun showLogoutDialog(prefManager: PrefManager) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout dari Zenspire?")
            .setPositiveButton("Logout") { dialog, _ ->
                prefManager.clear()
                val newIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(newIntent)
                activity?.finish()
            }.setNegativeButton("Batal"){ dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}
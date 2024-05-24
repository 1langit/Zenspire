package com.genzen.zenspire.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.databinding.FragmentHomeBinding
import com.genzen.zenspire.ui.questionnaire.QuestionnaireSplashActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager.getInstance(requireContext())

        with(binding) {
            txtName.text = prefManager.getFirstName()

            btnTest.setOnClickListener {
                val newIntent = Intent(requireContext(), QuestionnaireSplashActivity::class.java)
                startActivity(newIntent)
            }
        }
    }
}
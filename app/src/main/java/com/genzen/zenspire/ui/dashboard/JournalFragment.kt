package com.genzen.zenspire.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentJournalBinding
import com.genzen.zenspire.ui.common.TabAdapter
import com.genzen.zenspire.ui.journal.JournalAddActivity
import com.genzen.zenspire.ui.journal.JournalListFragment
import com.genzen.zenspire.ui.journal.JournalStatisticFragment
import com.google.android.material.tabs.TabLayoutMediator

class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJournalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewPager.adapter = TabAdapter(
                this@JournalFragment,
                arrayOf(JournalStatisticFragment(), JournalListFragment())
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Statistik"
                    1 -> "Journal Saya"
                    else -> ""
                }
            }.attach()

            fab.setOnClickListener {
                val newIntent = Intent(requireContext(), JournalAddActivity::class.java)
                startActivity(newIntent)
            }
        }
    }
}
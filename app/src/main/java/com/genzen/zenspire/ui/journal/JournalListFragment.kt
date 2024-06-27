package com.genzen.zenspire.ui.journal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentJournalListBinding

class JournalListFragment : Fragment() {

    private lateinit var binding: FragmentJournalListBinding
    private val journalViewModel: JournalViewModel by viewModels()
    private lateinit var journalAdapter: JournalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJournalListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalAdapter = JournalAdapter(requireContext()) { journal ->
            val newIntent = Intent(requireContext(), JournalDetailActivity::class.java)
            newIntent.putExtra("JOURNAL", journal)
            startActivity(newIntent)
        }

        with(binding) {
            rvJournal.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = journalAdapter
            }

            journalViewModel.journals.observe(viewLifecycleOwner) { journals ->
                journalAdapter.setJournals(journals)
            }
        }
    }
}
package com.genzen.zenspire.ui.journal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.FragmentJournalStatisticBinding

class JournalStatisticFragment : Fragment() {

    private lateinit var binding: FragmentJournalStatisticBinding
    private val journalViewModel: JournalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJournalStatisticBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val moodBadges = listOf(badgeExcellent, badgeGood, badgeNormal, badgeBad, badgeWorse)
            val progressBars = listOf(progressExcelent, progressGood, progressNormal, progressBad, progressWorse)

            journalViewModel.journals.observe(viewLifecycleOwner) { journals ->
                val points = listOf(
                    journals.count { it.mood == getString(R.string.angst_scale_1) },
                    journals.count { it.mood == getString(R.string.angst_scale_2) },
                    journals.count { it.mood == getString(R.string.angst_scale_3) },
                    journals.count { it.mood == getString(R.string.angst_scale_4) },
                    journals.count { it.mood == getString(R.string.angst_scale_5) },
                )

                moodBadges.forEach {
                    it.text = points[moodBadges.indexOf(it)].toString()
                }

                val totalPoints = points.sum()
                progressBars.forEachIndexed { index, progressBar ->
                    if (totalPoints > 0) {
                        val percentage = (points[index] / totalPoints.toFloat()) * 100
                        progressBar.progress = percentage.toInt()
                    } else {
                        progressBar.progress = 0
                    }
                }
            }
        }
    }
}
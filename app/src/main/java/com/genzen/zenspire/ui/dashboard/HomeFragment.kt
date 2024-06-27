package com.genzen.zenspire.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.databinding.FragmentHomeBinding
import com.genzen.zenspire.ui.article.ArticleActivity
import com.genzen.zenspire.ui.chat.ChatHistoryActivity
import com.genzen.zenspire.ui.chat.ChatActivity
import com.genzen.zenspire.ui.journal.JournalAddActivity
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

            setMoodButtonClickListener(cardMood.btnMoodExcellent)
            setMoodButtonClickListener(cardMood.btnMoodGood)
            setMoodButtonClickListener(cardMood.btnMoodNormal)
            setMoodButtonClickListener(cardMood.btnMoodBad)
            setMoodButtonClickListener(cardMood.btnMoodWorse)

            btnChatbot.setOnClickListener {
                val newIntent = Intent(requireContext(), ChatActivity::class.java)
                startActivity(newIntent)
            }

            btnChatPsych.setOnClickListener {
                val newIntent = Intent(requireContext(), ChatHistoryActivity::class.java)
                startActivity(newIntent)
            }

            btnArticle.setOnClickListener {
                val newIntent = Intent(requireContext(), ArticleActivity::class.java)
                startActivity(newIntent)
            }

            btnClinic.setOnClickListener {  }

//            btnTest.setOnClickListener {
//                val newIntent = Intent(requireContext(), QuestionnaireSplashActivity::class.java)
//                startActivity(newIntent)
//            }

            btnOtherPost.setOnClickListener {
//                val parentActivity = requireActivity() as DashboardActivity
//                parentActivity.navigateToPage("community")
            }

            btnOtherArticle.setOnClickListener {
                val newIntent = Intent(requireContext(), ArticleActivity::class.java)
                startActivity(newIntent)
            }
        }
    }

    private fun setMoodButtonClickListener(button: Button) {
        button.setOnClickListener {
            val newIntent = Intent(requireContext(), JournalAddActivity::class.java)
            newIntent.putExtra("MOOD", button.text)
            startActivity(newIntent)
        }
    }
}
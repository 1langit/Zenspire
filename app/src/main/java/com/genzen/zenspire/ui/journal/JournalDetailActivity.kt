package com.genzen.zenspire.ui.journal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.community.Post
import com.genzen.zenspire.data.models.journal.Journal
import com.genzen.zenspire.databinding.ActivityJournalDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class JournalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJournalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val article: Journal? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("JOURNAL", Journal::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("JOURNAL") as? Journal
        }

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            article?.let {
                txtDateTime.text = it.created_at
                imgMood.text = it.mood
                txtTitleContent.text = it.title
                txtQ1Content.text = it.question_1
                txtQ2Content.text = it.question_2
                txtQ3Content.text = it.question_3
                txtQ4Content.text = it.question_4

                imgMood.setCompoundDrawablesRelativeWithIntrinsicBounds(0,
                    when (it.mood) {
                        getString(R.string.angst_scale_1) -> R.drawable.ic_mood_sangat_baik
                        getString(R.string.angst_scale_2) -> R.drawable.ic_mood_baik
                        getString(R.string.angst_scale_3) -> R.drawable.ic_mood_biasa
                        getString(R.string.angst_scale_4) -> R.drawable.ic_mood_buruk
                        getString(R.string.angst_scale_5) -> R.drawable.ic_mood_sangat_buruk
                        else -> 0
                    },
                    0, 0
                )
            }

            btnEdit.setOnClickListener {
                val newIntent = Intent(this@JournalDetailActivity, JournalAddActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            btnAnalysis.setOnClickListener {
                val inflater: LayoutInflater = LayoutInflater.from(this@JournalDetailActivity)
                val customView: View = inflater.inflate(R.layout.dialog_analysis, null)
                customView.findViewById<TextView>(R.id.txt_title).text = "AI Analisis"

                MaterialAlertDialogBuilder(this@JournalDetailActivity)
                    .setView(customView)
                    .setPositiveButton("Tutup") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }
}
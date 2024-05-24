package com.genzen.zenspire.ui.journal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
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

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnEdit.setOnClickListener {
                val newIntent = Intent(this@JournalDetailActivity, JournalAddActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            btnAnalysis.setOnClickListener {
                MaterialAlertDialogBuilder(this@JournalDetailActivity)
                    .setTitle("Analisis AI")
                    .setMessage(R.string.lorem)
                    .setPositiveButton("Tutup") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }
}
package com.genzen.zenspire.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityQuestionnaireSplashBinding

class QuestionnaireSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionnaireSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            btnStart.setOnClickListener {
                val newIntent = Intent(this@QuestionnaireSplashActivity, QuestionnaireActivity::class.java)
                startActivity(newIntent)
            }

            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}
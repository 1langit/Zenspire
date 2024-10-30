package com.genzen.zenspire.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityQuestionnaireResultBinding
import com.genzen.zenspire.ui.dashboard.DashboardActivity

class QuestionnaireResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            btnShowHide.setOnClickListener {
                rvResult.visibility = if (rvResult.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            btnRecheck.setOnClickListener {
                val newIntent = Intent(this@QuestionnaireResultActivity, QuestionnaireActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            btnClose.setOnClickListener {
                val newIntent = Intent(this@QuestionnaireResultActivity, DashboardActivity::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }
        }
    }
}
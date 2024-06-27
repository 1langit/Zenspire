package com.genzen.zenspire.ui.journal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.data.models.journal.Journal
import com.genzen.zenspire.databinding.ActivityJournalAddBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class JournalAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalAddBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var timePicker: MaterialTimePicker
    private lateinit var buttons: List<Button>
    private lateinit var colors: Map<Button, Int>
    private lateinit var drawables: Map<Button, Int>
    private val journalViewModel: JournalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJournalAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefManager = PrefManager.getInstance(this@JournalAddActivity)
        var selectedMood = intent.getStringExtra("MOOD")
        initializeDatePicker()
        initializeTimePicker()
        setCurrentDateAndTime()

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            edtDate.setOnClickListener {
                datePicker.show(supportFragmentManager, "DatePicker")
            }

            inputDate.setEndIconOnClickListener {
                datePicker.show(supportFragmentManager, "DatePicker")
            }

            edtTime.setOnClickListener {
                timePicker.show(supportFragmentManager, "TimePicker")
            }

            inputTime.setEndIconOnClickListener {
                timePicker.show(supportFragmentManager, "TimePicker")
            }

            cardMood.apply {
                buttons = listOf(
                    btnMoodExcellent,
                    btnMoodGood,
                    btnMoodNormal,
                    btnMoodBad,
                    btnMoodWorse
                )

                colors = mapOf(
                    btnMoodExcellent to R.color.primary_blue_600,
                    btnMoodGood to R.color.primary_green_1,
                    btnMoodNormal to R.color.secondary_green_700,
                    btnMoodBad to R.color.secondary_yellow_400,
                    btnMoodWorse to R.color.secondary_red_400
                )

                drawables = mapOf(
                    btnMoodExcellent to R.drawable.ic_mood_sangat_baik,
                    btnMoodGood to R.drawable.ic_mood_baik,
                    btnMoodNormal to R.drawable.ic_mood_biasa,
                    btnMoodBad to R.drawable.ic_mood_buruk,
                    btnMoodWorse to R.drawable.ic_mood_sangat_buruk
                )

                buttons.forEach { button ->
                    button.setOnClickListener {
                        selectedMood = (it as Button).text.toString()
                        updateButtonStates(it)
                    }
                }
            }

            btnCancel.setOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                val journal = Journal(
                    0,
                    prefManager.getUid(),
                    edtTitle.text.toString(),
                    selectedMood ?: "",
                    edtQ1.text.toString(),
                    edtQ2.text.toString(),
                    edtQ3.text.toString(),
                    edtQ4.text.toString(),
                    "${edtDate.text.toString()} • ${edtTime.text.toString()}",
                    ""
                )

                if (title.isNotBlank() && !selectedMood.isNullOrBlank()) {
                    journalViewModel.addJournal(journal)
                    Toast.makeText(this@JournalAddActivity, "Jurnal disimpan", Toast.LENGTH_SHORT).show()
                    val newIntent = Intent(this@JournalAddActivity, JournalDetailActivity::class.java)
                    newIntent.putExtra("JOURNAL", journal)
                    startActivity(newIntent)
                    finish()
                }
            }

            buttons.forEach { button ->
                if (button.text == selectedMood) updateButtonStates(button)
            }
        }
    }

    private fun initializeDatePicker() {
        val currentDateInMillis = MaterialDatePicker.todayInUtcMilliseconds()

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih tanggal")
            .setSelection(currentDateInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormatter.format(Date(selection))
            binding.edtDate.setText(formattedDate)
        }
    }

    private fun initializeTimePicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        timePicker = MaterialTimePicker.Builder()
            .setTitleText("Pilih waktu")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(currentHour)
            .setMinute(currentMinute)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.edtTime.setText(formattedTime)
        }
    }

    private fun setCurrentDateAndTime() {
        // Set current date
        val currentDateInMillis = MaterialDatePicker.todayInUtcMilliseconds()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormatter.format(Date(currentDateInMillis))
        binding.edtDate.setText(formattedDate)

        // Set current time
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute)
        binding.edtTime.setText(formattedTime)
    }

    private fun updateButtonStates(activeButton: Button) {
        buttons.forEach { button ->
            if (button == activeButton) {
                button.setBackgroundColor(ContextCompat.getColor(this@JournalAddActivity, colors[button]!!))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
                val wrappedDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this@JournalAddActivity, drawables[button]!!)!!)
                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this@JournalAddActivity, R.color.white))
                button.setCompoundDrawablesWithIntrinsicBounds(null, wrappedDrawable, null, null)
            } else {
                button.setBackgroundColor(ContextCompat.getColor(this@JournalAddActivity, R.color.white))
                button.setTextColor(ContextCompat.getColor(this, colors[button]!!))
                val wrappedDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this@JournalAddActivity, drawables[button]!!)!!)
                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this@JournalAddActivity, colors[button]!!))
                button.setCompoundDrawablesWithIntrinsicBounds(null, wrappedDrawable, null, null)
            }
        }
    }
}
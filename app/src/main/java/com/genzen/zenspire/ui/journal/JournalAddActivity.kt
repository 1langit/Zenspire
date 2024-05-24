package com.genzen.zenspire.ui.journal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
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

            btnCancel.setOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                Toast.makeText(this@JournalAddActivity, "Jurnal disimpan", Toast.LENGTH_SHORT).show()
                val newIntent = Intent(this@JournalAddActivity, JournalDetailActivity::class.java)
                startActivity(newIntent)
                finish()
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
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
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
        val formattedTime = String.format("%02d:%02d", currentHour, currentMinute)
        binding.edtTime.setText(formattedTime)
    }
}
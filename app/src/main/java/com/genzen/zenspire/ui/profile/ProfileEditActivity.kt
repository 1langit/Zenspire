package com.genzen.zenspire.ui.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.R
import com.genzen.zenspire.data.PrefManager
import com.genzen.zenspire.databinding.ActivityProfileEditBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefManager = PrefManager.getInstance(this@ProfileEditActivity)
        initializeDatePicker()

        with(binding) {
            edtFirstName.setText(prefManager.getFirstName())
            edtLastName.setText(prefManager.getLastName())
            edtBirthDate.setText("prefManager.getBirthdate()")
            edtGender.setText(if (prefManager.getGender() == "L") "Laki-laki" else "Perempuan")
            edtEmail.setText(prefManager.getEmail())
            edtPassword.setText("***")
            edtGender.setSimpleItems(R.array.gender)

            topAppBar.setNavigationOnClickListener {
                finish()
            }

            edtBirthDate.setOnClickListener {
                datePicker.show(supportFragmentManager, "DatePicker")
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
            binding.edtBirthDate.setText(formattedDate)
        }
    }
}
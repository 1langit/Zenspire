package com.genzen.zenspire.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.ApiClient
import com.genzen.zenspire.PrefManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityPersonalDataBinding
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeDatePicker()

        with(binding) {
            edtBirthDate.setOnClickListener {
                datePicker.show(supportFragmentManager, "datePicker")
            }

            inputBirthDate.setEndIconOnClickListener {
                datePicker.show(supportFragmentManager, "datePicker")
            }

            btnSkip.setOnClickListener {
                val newIntent = Intent(this@PersonalDataActivity, DashboardActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            btnSave.setOnClickListener {
                val birthDate = edtBirthDate.text.toString()
                val genderRadioSelectedId = radioGroupGender.checkedRadioButtonId
                val checkBoxes = listOf(checkAsthma, checkHeart, checkDiabetes, checkObesityMalnutrition, checkOther)
                val healthCondition = checkBoxes.filter { it.isChecked }.map { it.text.toString() }

                if (validateUserData(birthDate, genderRadioSelectedId)) {
                    val gender = when(findViewById<RadioButton>(genderRadioSelectedId).text.toString()) {
                        "Laki-laki" -> "L"
                        "Perempuan" -> "P"
                        else -> ""
                    }
                    saveUserData(birthDate, gender, healthCondition)
                }
            }
        }
    }

    private fun initializeDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Kapan kamu lahir?")
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormatter.format(Date(selection))
            binding.edtBirthDate.setText(formattedDate)
        }
    }

    private fun saveUserData(birthDate: String, gender: String, healthCondition: List<String>) {
        val prefManager = PrefManager.getInstance(this@PersonalDataActivity)
        val userDataRequest = UserDataRequest(birthDate, gender, healthCondition)
        val userDataCall = ApiClient.getApiInstance().postUserData("Bearer ${prefManager.getToken()}", userDataRequest)
        userDataCall.enqueue(object: Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Toast.makeText(this@PersonalDataActivity, "Data disimpan", Toast.LENGTH_SHORT).show()
                val newIntent = Intent(this@PersonalDataActivity, DashboardActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@PersonalDataActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateUserData(birthDate: String, genderId: Int): Boolean {
        with(binding) {
            inputBirthDate.error = if (birthDate.isBlank()) "Tidak boleh kosong" else null
            inputBirthDate.isErrorEnabled = birthDate.isBlank()

            txtRadioError.visibility = if (genderId == -1) View.VISIBLE else View.GONE

            return birthDate.isNotBlank() && genderId != -1
        }
    }
}
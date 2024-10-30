package com.genzen.zenspire.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.data.api.ApiClient
import com.genzen.zenspire.R
import com.genzen.zenspire.data.models.auth.RegisterRequest
import com.genzen.zenspire.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = getColor(R.color.primary_blue_50)

        with(binding) {
            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString()
                val firstName = edtFirstName.text.toString()
                val lastName = edtLastName.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = edtConfirmPassword.text.toString()
                if (validateRegister(email, firstName, password, confirmPassword)) {
                    registerUser(email, firstName, lastName, password)
//                    stub()
                }
            }

            btnLogin.setOnClickListener {
                val newIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(newIntent)
                finish()
            }
        }
    }

    private fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ) {
        val registerRequest = RegisterRequest(
            email = email,
            password = password,
            first_name = firstName,
            last_name = lastName.ifBlank { null }
        )
        val registerCall = ApiClient.getApiInstance().register(registerRequest)
        registerCall.enqueue(object: Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Toast.makeText(this@RegisterActivity, "Daftar sukses, silahkan masuk", Toast.LENGTH_SHORT).show()
                val newIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(newIntent)
                finish()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateRegister(
        email: String,
        firstName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        with(binding) {
            inputEmail.error = if (email.isBlank()) "Tidak boleh kosong" else null
            inputEmail.isErrorEnabled = email.isBlank()

            inputFirstName.error = if (firstName.isBlank()) "Tidak boleh kosong" else null
            inputFirstName.isErrorEnabled = firstName.isBlank()

            inputPassword.error = if (password.isBlank()) "Tidak boleh kosong" else null
            inputPassword.isErrorEnabled = password.isBlank()

            inputConfirmPassword.error = if (password != confirmPassword) "Password tidak sama" else null
            inputConfirmPassword.isErrorEnabled = password != confirmPassword

            return email.isNotBlank() && firstName.isNotBlank() && password.isNotBlank() && password == confirmPassword
        }
    }

    private fun stub() {
        Toast.makeText(this@RegisterActivity, "Daftar sukses, silahkan masuk", Toast.LENGTH_SHORT).show()
        val newIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(newIntent)
        finish()
    }
}
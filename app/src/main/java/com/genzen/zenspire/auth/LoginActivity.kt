package com.genzen.zenspire.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.ApiClient
import com.genzen.zenspire.PrefManager
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityLoginBinding
import com.genzen.zenspire.dashboard.DashboardActivity
import com.genzen.zenspire.dashboard.PersonalDataActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (isLoggedin()) {
            val newIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(newIntent)
            finish()
        }

        with(binding) {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if (validateLogin(email, password)) {
                    loginUser(email, password)
                }
            }
            btnRegister.setOnClickListener {
                val newIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(newIntent)
                finish()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val loginCall = ApiClient.getApiInstance().login(loginRequest)
        loginCall.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    val prefManager = PrefManager.getInstance(this@LoginActivity)
                    prefManager.apply {
                        saveToken(response.body()!!.token)
                        saveUid(data.id)
                        saveEmail(data.email)
                        saveFirstName(data.first_name)
                        saveLastName(data.last_name)
                        saveExp(data.experience_points)
                    }
                    Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                    val newIntent = Intent(this@LoginActivity, PersonalDataActivity::class.java)
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(newIntent)
                } else {
                    val errorJson = JSONObject(response.errorBody()?.string())
                    val errorMessage = errorJson.getString("error")
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateLogin(email: String, password: String): Boolean {
        with(binding) {
            inputEmail.error = if (email.isBlank()) "Tidak boleh kosong" else null
            inputEmail.isErrorEnabled = email.isBlank()

            inputPassword.error = if (password.isBlank()) "Tidak boleh kosong" else null
            inputPassword.isErrorEnabled = password.isBlank()

            return email.isNotBlank() && password.isNotBlank()
        }
    }

    private fun isLoggedin(): Boolean {
        val prefManager = PrefManager.getInstance(this)
        return prefManager.getToken().isNotBlank()
    }
}
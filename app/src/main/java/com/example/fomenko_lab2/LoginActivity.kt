package com.example.fomenko_lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fomenko_lab2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        binding.signupText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Успішна авторизація!", Toast.LENGTH_SHORT).show()
                            saveUserName(email)
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this, "Поля повинні бути заповнені!", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun saveUserName(userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", userName)
        editor.apply()
    }
}

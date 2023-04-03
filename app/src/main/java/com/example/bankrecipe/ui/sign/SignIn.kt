package com.example.bankrecipe.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankrecipe.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {

        }
    }
}
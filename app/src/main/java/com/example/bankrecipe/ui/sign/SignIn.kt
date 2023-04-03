package com.example.bankrecipe.ui.sign

import android.content.Intent
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
            //로그인 파트

        }
        binding.signInToSignUp.setOnClickListener { //회원가입
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        binding.resetPassword.setOnClickListener{

        }
    }
}
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
        binding.signInToSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            //회원가입 액티비티 호출
        }
        binding.resetPassword.setOnClickListener{
            //비밀번호 재설정 액티비티 이동
        }
        binding.btnToMainActivity.setOnClickListener {
            onBackPressed()
        }
    }
}
package com.example.bankrecipe.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivitySelectSignUpTypeBinding
import com.example.bankrecipe.databinding.ActivitySignUpSellerBinding

class SignUpSeller : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpSellerBinding
    private lateinit var BackBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BackBtn = binding.SignUpSellerBackBtn

        BackBtn.setOnClickListener {
            onBackPressed()
        }
    }
}
package com.example.bankrecipe.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityMapBinding
import com.example.bankrecipe.databinding.ActivitySelectSignUpTypeBinding

class SelectSignUpType : AppCompatActivity() {
    private lateinit var binding: ActivitySelectSignUpTypeBinding
    private lateinit var SelectedType : String //선택된 사용자 유형
    private lateinit var radiogrp : RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSignUpTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radiogrp = binding.selectTypeRadioGroup
        radiogrp.setOnCheckedChangeListener { group, checkedId ->
            Log.d("dlog","라디오 버튼 클릭")
            when(checkedId){
                //R.id.radio1 -> bidning.textView
            }
        }

    }
    //유지 후 선택에 따라 다른 activity를 적용
}
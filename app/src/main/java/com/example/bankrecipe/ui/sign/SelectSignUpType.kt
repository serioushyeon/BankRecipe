package com.example.bankrecipe.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.bankrecipe.MainActivity
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityMapBinding
import com.example.bankrecipe.databinding.ActivitySelectSignUpTypeBinding

class SelectSignUpType : AppCompatActivity() {
    private lateinit var binding: ActivitySelectSignUpTypeBinding
    private lateinit var SelectedType : String //선택된 사용자 유형
    private lateinit var radiogrp : RadioGroup
    private lateinit var sellerSelectBtn : ImageButton
    private lateinit var userSelectBtn : ImageButton
    private lateinit var sellerRadioBtn : RadioButton
    private lateinit var userRadioBtn : RadioButton
    private lateinit var type : String
    private lateinit var signUpNextBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSignUpTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sellerSelectBtn = binding.sellerIconbtn
        userSelectBtn = binding.generalUserIconbtn
        radiogrp = binding.selectTypeRadioGroup
        sellerRadioBtn = binding.radioButtonSeller
        userRadioBtn = binding.radioButtonUser
        signUpNextBtn = binding.signUpNextBtn
        type = "" //기본값

        sellerSelectBtn.setOnClickListener {

        }
        userSelectBtn.setOnClickListener {

        }

        radiogrp.setOnCheckedChangeListener { group, checkedId ->
            Log.d("dlog","라디오 버튼 클릭")
            when(checkedId){
                //R.id.radio1 -> bidning.textView
                R.id.radioButtonSeller -> type = "seller"
                R.id.radioButtonUser -> type = "user"
            }
        }
        signUpNextBtn.setOnClickListener { 
            if(type.equals("seller")){
                val intent = Intent(this, SignUpSeller::class.java)
                intent.putExtra("type", type)
                startActivity(intent)
            } else if(type.equals("user")){
                val intent = Intent(this,SignUpUser::class.java)
                intent.putExtra("type",type)
                startActivity(intent)
            } else{ //어느 것도 선택되지 않은 경우
                Toast.makeText(this,"사용자 유형을 선택해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

    }
    //유지 후 선택에 따라 다른 activity를 적용
}
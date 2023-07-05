package com.solid.bankrecipe.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.solid.bankrecipe.MainActivity
import com.solid.bankrecipe.R
import com.solid.bankrecipe.databinding.ActivityMapBinding
import com.solid.bankrecipe.databinding.ActivitySelectSignUpTypeBinding

    private lateinit var type : String
class SelectSignUpType : AppCompatActivity() {
    private lateinit var binding: ActivitySelectSignUpTypeBinding
    private lateinit var SelectedType : String //선택된 사용자 유형
    private lateinit var radiogrp : RadioGroup
    private lateinit var sellerSelectBtn : ImageButton
    private lateinit var userSelectBtn : ImageButton
    private lateinit var sellerRadioBtn : RadioButton
    private lateinit var userRadioBtn : RadioButton

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
        radiogrp.setOnCheckedChangeListener { group, checkedId ->
            Log.d("dlog","라디오 버튼 클릭")
            when(checkedId){
                R.id.radioButtonSeller -> chSeller()
                R.id.radioButtonUser -> chUser()
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

    private fun chUser(){
        type = "user"
        binding.expTxt.text = "● 누구나 가능합니다.\n\n● 내가 만든 요리를 공유할 수 있습니다.\n\n● 예산에 맞는 레시피를 볼 수 있습니다."
    }
    private fun chSeller(){
        type = "seller"
        binding.expTxt.text = "● 사업자등록을 마친 사업자만 가능합니다.\n\n● 내 가게를 홍보할 수 있습니다.\n\n"
    }
}
package com.example.bankrecipe.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bankrecipe.databinding.ActivitySignInBinding
import com.example.bankrecipe.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpConfirm.setOnClickListener {
            val userName = binding.signUpUsername.text.toString()
            val userID = binding.signUpUserID.text.toString()
            val userPassword = binding.signUpUserPassword.text.toString()
            val userPassword2 = binding.signUpUserPassword2.text.toString()
            val userEmail = binding.signUpEmail.text.toString()
            if(userName.isEmpty()) //제약조건
                Toast.makeText(this,"이름을 입력하세요.", Toast.LENGTH_SHORT)
            else if(userID.isEmpty())
                Toast.makeText(this,"아이디를 입력하세요.", Toast.LENGTH_SHORT)
            else if(userPassword.isEmpty())
                Toast.makeText(this,"비밀번호를 입력하세요.", Toast.LENGTH_SHORT)
            else if(userPassword2.isEmpty())
                Toast.makeText(this,"비밀번호 재확인이 필요합니다.", Toast.LENGTH_SHORT)
            else if(userEmail.isEmpty())
                Toast.makeText(this,"이메일이 필요합니다.", Toast.LENGTH_SHORT)
            else if(userPassword != userPassword2)
                Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT)
            else
                createAccount(userName,userPassword,userEmail)
            //다이얼로그 띄우기 : 계정 생성 후 바로 로그인할지 아닐지 결정하는 부분을 띄울지 선택하기
        }
        binding.btnSignUpBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun createAccount(Name:String, password:String,Email:String){
        //파이어베이스 계정 등록
    }
}
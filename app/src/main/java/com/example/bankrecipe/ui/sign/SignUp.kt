package com.example.bankrecipe.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bankrecipe.MainActivity
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
                Toast.makeText(this,"이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userID.isEmpty())
                Toast.makeText(this,"아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userPassword.isEmpty())
                Toast.makeText(this,"비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userPassword2.isEmpty())
                Toast.makeText(this,"비밀번호 재확인이 필요합니다.", Toast.LENGTH_SHORT).show()
            else if(userEmail.isEmpty())
                Toast.makeText(this,"이메일이 필요합니다.", Toast.LENGTH_SHORT).show()
            else if(userPassword != userPassword2)
                Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            else
                createAccount(userName,userPassword,userEmail)
            //다이얼로그 띄우기 : 계정 생성 후 바로 로그인할지 아닐지 결정하는 부분을 띄울지 선택하기
        }
        binding.btnSignUpBack.setOnClickListener {
            //onBackPressed()
            val intent = Intent(this, MainActivity::class.java) //테스트 중
            intent.putExtra("key","문자열")
            finishAffinity() //새로운 스택생성 x, 기존 스택 제거
            startActivity(intent)
        }
    }
    private fun createAccount(Name:String, password:String,Email:String){
        //파이어베이스 계정 등록
        //일단 회원가입이 성공했다고 가정을 하고  회원가입 성공을 알림.
        //다이얼로그는 불필요할 것 같다.
        //바로 로그인하고 메인액티비티 이동.

    }
}
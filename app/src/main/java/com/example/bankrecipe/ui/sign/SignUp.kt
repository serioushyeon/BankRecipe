package com.example.bankrecipe.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bankrecipe.MainActivity
import com.example.bankrecipe.databinding.ActivitySignInBinding
import com.example.bankrecipe.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
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
            else if(userID.isEmpty()) //현재 이메일과 아이디도 동일하게 두는 중.
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
            onBackPressed()
        }
    }
    private fun createAccount(Name:String, password:String,Email:String){
        //회원가입이 성공했다고 가정을 하고  회원가입 성공을 알림.
        //다이얼로그 없이 바로 로그인하고 메인액티비티 이동.
        auth?.createUserWithEmailAndPassword(Email,password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    MoveMainActivity(auth?.currentUser)
                    Toast.makeText(this, "회원가입 성공"+"\n"+"$Name"+"님 환영합니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun MoveMainActivity(user: FirebaseUser?){
        if(user!=null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userUid","$user")
            finishAffinity() //새로운 스택을 생성하지 않고 기존 스택 제거
            startActivity(intent)
        }
    }
}
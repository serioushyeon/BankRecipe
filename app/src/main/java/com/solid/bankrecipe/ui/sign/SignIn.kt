package com.solid.bankrecipe.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.solid.bankrecipe.MainActivity
import com.solid.bankrecipe.Utils.FBAuth
import com.solid.bankrecipe.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.btnSignUp.setOnClickListener {
            //로그인 파트
            val email = binding.signInUserEmail.text.toString()
            val password = binding.signInUserPassword.text.toString()
            signIn(email, password)

        }
        binding.signInToSignUp.setOnClickListener {
            //val intent = Intent(this, SignUpUser::class.java)    사용자 구분 없이 가입했었음(기존)
            val intent = Intent(this, SelectSignUpType::class.java) //변경: 판매자와 일반 사용자를 구분
            startActivity(intent)
            //회원가입 액티비티 호출
        }
        binding.resetPassword.setOnClickListener{
            //비밀번호 재설정 액티비티 이동
        }
        binding.btnToMainActivity.setOnClickListener {
            val intent = Intent(this, com.solid.bankrecipe.MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
    private fun signIn(email: String, password: String){
        if(email.equals("") || password.equals("")){
            Toast.makeText(this," 아이디나 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
        }
        else{
            auth?.signInWithEmailAndPassword(email,password)
                ?.addOnCompleteListener(this) { task ->
                    //유저 정보 가져오기
                    if(task.isSuccessful){
                        MoveMainActivity(auth?.currentUser)
                        Toast.makeText(
                            this, "${FBAuth.getDisplayName()}"+"님 환영합니다.", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(
                            this, "없는 아이디 또는 틀린 비밀번호입니다..", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun MoveMainActivity(user: FirebaseUser?){
        if(user!=null){
            val intent = Intent(this, com.solid.bankrecipe.MainActivity::class.java)
            intent.putExtra("userUid","${user.uid}")
            finishAffinity()
            startActivity(intent)
            //테스트
        }
    }
}
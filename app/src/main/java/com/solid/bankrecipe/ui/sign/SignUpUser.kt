package com.solid.bankrecipe.ui.sign

import android.app.Person
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.solid.bankrecipe.MainActivity
import com.solid.bankrecipe.Utils.FBAuth
import com.solid.bankrecipe.databinding.ActivitySignUpUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpUser : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var fbFirestore : FirebaseFirestore? = null
    private lateinit var binding: ActivitySignUpUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        fbFirestore = FirebaseFirestore.getInstance()
        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpConfirm.setOnClickListener {
            var userInfo = PersonalData()
            userInfo.ID = binding.signUpUserID.text.toString()
            userInfo.UserName = binding.signUpUsername.text.toString()
            userInfo.Password = binding.signUpUserPassword.text.toString()
            val userPassword2 = binding.signUpUserPassword2.text.toString()
            userInfo.userType = "user"
            userInfo.businessType = "null"
            userInfo.corporationLocation = "null"
            userInfo.corporationName = "null"
            userInfo.businessNumber = "null"
            //userInfo.userUid = auth?.uid
            userInfo.Email = binding.signUpEmail.text.toString()
            if(userInfo.UserName.toString().isEmpty()) //제약조건
                Toast.makeText(this,"이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userInfo.ID.toString().isEmpty()) //현재 이메일과 아이디도 동일하게 두는 중.
                Toast.makeText(this,"아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userInfo.Password.toString().isEmpty())
                Toast.makeText(this,"비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            else if(userPassword2.isEmpty())
                Toast.makeText(this,"비밀번호 재확인이 필요합니다.", Toast.LENGTH_SHORT).show()
            else if(userInfo.Email.toString().isEmpty())
                Toast.makeText(this,"이메일이 필요합니다.", Toast.LENGTH_SHORT).show()
            else if(userInfo.Password.toString() != userPassword2)
                Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            else
                createAccount(userInfo)
            //다이얼로그 띄우기 : 계정 생성 후 바로 로그인할지 아닐지 결정하는 부분을 띄울지 선택하기
        }
    }

    private fun enrollUserInformation(userInfo : PersonalData){ //유저정보 업데이트
        val profileUpdates = userProfileChangeRequest { 
            displayName = userInfo.UserName
        }
        val user = FBAuth.auth.currentUser
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                userInfo.userUid = FBAuth.getUid()
                fbFirestore?.collection("user")?.document(userInfo.userUid.toString())?.set(userInfo)
            }
        }
    }
    private fun createAccount(userInfo : PersonalData){
        //회원가입 성공을 알림.
        //로그인하고 메인액티비티 이동.
        auth?.createUserWithEmailAndPassword(userInfo.Email.toString(),userInfo.Password.toString())
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    enrollUserInformation(userInfo) //유저정보 업데이트 하고
                    MoveMainActivity(auth?.currentUser)
                    Toast.makeText(this, "회원가입 성공"+"\n"+"${userInfo.UserName.toString()}"+"님 환영합니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun MoveMainActivity(user: FirebaseUser?){
        if(user!=null){
            val intent = Intent(this, com.solid.bankrecipe.MainActivity::class.java)
            intent.putExtra("userUid","${user.uid}")
            finishAffinity() //새로운 스택을 생성하지 않고 기존 스택 제거
            startActivity(intent)
        }
    }
}
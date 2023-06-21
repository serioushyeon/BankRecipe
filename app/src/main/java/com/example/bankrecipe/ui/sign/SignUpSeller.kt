package com.example.bankrecipe.ui.sign

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.bankrecipe.MainActivity
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.ActivitySelectSignUpTypeBinding
import com.example.bankrecipe.databinding.ActivitySignUpSellerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpSeller : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var fbFirestore : FirebaseFirestore? = null
    private lateinit var binding: ActivitySignUpSellerBinding
    private lateinit var signUpSellerBtn : Button
    private lateinit var BackBtn : Button
    private lateinit var emailHead : String
    private lateinit var emailDomain : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        fbFirestore = FirebaseFirestore.getInstance()
        binding = ActivitySignUpSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BackBtn = binding.SignUpSellerBackBtn
        signUpSellerBtn = binding.signUpSellerBtn
        BackBtn.setOnClickListener {
            onBackPressed()
        }
        signUpSellerBtn.setOnClickListener {
            var userInfo = PersonalData()
            emailHead = binding.representativeName.text.toString()
            emailDomain = binding.emailDomain.text.toString()
            userInfo.businessNumber = binding.businessNumber.text.toString()
            userInfo.corporationName = binding.corporationName.text.toString()
            userInfo.corporationLocation = binding.corporationLocation.text.toString()
            userInfo.businessType = binding.businessType.text.toString()
            userInfo.userType = "seller"
            userInfo.UserName = binding.representativeName.text.toString() //대표자 명, 이름
            userInfo.ID = binding.signUpSellerId.text.toString()
            userInfo.Password = binding.signUpSellerPassword.text.toString()

            if(userInfo.businessNumber.equals("")||userInfo.businessNumber.toString().length!=10){ //사업자 등록번호 오류
                Toast.makeText(this," 사업자 등록번호 확인 필요.", Toast.LENGTH_SHORT).show()
            } else if(userInfo.corporationName.equals("")){
                Toast.makeText(this,"상호명 확인 필요.",Toast.LENGTH_SHORT).show()
            } else if(userInfo.corporationLocation.equals("")){
                Toast.makeText(this," 소재지 미등록.",Toast.LENGTH_SHORT).show()
            } else if(userInfo.businessType.equals("")){
                Toast.makeText(this," 업태를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(userInfo.UserName.equals("")){
                Toast.makeText(this," 대표자명 확인 필요.",Toast.LENGTH_SHORT).show()
            } else if(emailHead.equals("")||emailDomain.equals("")){
                Toast.makeText(this," 이메일 주소를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(userInfo.ID.equals("")){
                Toast.makeText(this," 아이디를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(userInfo.Password.equals("")){
                Toast.makeText(this," 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                userInfo.Email = emailHead+"@"+emailDomain
                createAccount(userInfo)
            }

        }

    }

    private fun enrollUserInformation(UserInfo : PersonalData){ //유저정보 업데이트
        val profileUpdates = userProfileChangeRequest {
            displayName = UserInfo.UserName
        }
        val user = FBAuth.auth.currentUser
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                UserInfo.userUid = FBAuth.getUid()
                fbFirestore?.collection("user")?.document(UserInfo.userUid.toString())?.set(UserInfo)
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
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userUid","${user.uid}")
            finishAffinity() //새로운 스택을 생성하지 않고 기존 스택 제거
            startActivity(intent)
        }
    }
}
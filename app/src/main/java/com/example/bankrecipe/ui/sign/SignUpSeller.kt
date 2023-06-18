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
    private lateinit var businessNumber : String
    private lateinit var corporationName : String
    private lateinit var corporationLocation : String
    private lateinit var businessType : String
    private lateinit var representativeName : String
    private lateinit var emailHead : String
    private lateinit var emailDomain : String
    private lateinit var emailaddress : String //head+ @ + domain
    private lateinit var ID : String
    private lateinit var Password : String
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
            businessNumber = binding.businessNumber.text.toString()
            corporationName = binding.corporationName.text.toString()
            corporationLocation = binding.corporationLocation.text.toString()
            businessType = binding.businessType.text.toString()
            representativeName = binding.representativeName.text.toString()
            emailHead = binding.representativeName.text.toString()
            emailDomain = binding.emailDomain.text.toString()
            ID = binding.signUpSellerId.text.toString()
            Password = binding.signUpSellerPassword.text.toString()


            if(businessNumber.equals("")||businessNumber.length!=10){ //사업자 등록번호 오류
                Toast.makeText(this," 사업자 등록번호 확인 필요.", Toast.LENGTH_SHORT).show()
            } else if(corporationName.equals("")){
                Toast.makeText(this,"상호명 확인 필요.",Toast.LENGTH_SHORT).show()
            } else if(corporationLocation.equals("")){
                Toast.makeText(this," 소재지 미등록.",Toast.LENGTH_SHORT).show()
            } else if(businessType.equals("")){
                Toast.makeText(this," 업태를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(representativeName.equals("")){
                Toast.makeText(this," 대표자명 확인 필요.",Toast.LENGTH_SHORT).show()
            } else if(emailHead.equals("")||emailDomain.equals("")){
                Toast.makeText(this," 이메일 주소를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(ID.equals("")){
                Toast.makeText(this," 아이디를 확인해주세요.",Toast.LENGTH_SHORT).show()
            } else if(Password.equals("")){
                Toast.makeText(this," 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                emailaddress = emailHead+"@"+emailDomain
            }

        }

    }
    private fun createAccount(Name : String, Email : String, BusinessNumber : String, CorporationName : String, CorporationLocation : String, //그냥 추가함
                              BusinessType : String, Password : String){
        //회원가입 성공을 알림.
        //로그인하고 메인액티비티 이동.
        auth?.createUserWithEmailAndPassword(Email,Password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    enrollUserInformation(Name) //유저정보 업데이트 하고
                    MoveMainActivity(auth?.currentUser)
                    Toast.makeText(this, "회원가입 성공"+"\n"+"${FBAuth.getDisplayName()}"+"님 환영합니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun enrollUserInformation(Name:String){ //유저정보 업데이트
        val profileUpdates = userProfileChangeRequest {
            displayName = Name
        }
        val user = FBAuth.auth.currentUser
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                //회원가입성공했을때 유저 정보 넣는거니까 성공했을땐 아무것도 할 게 없다.
                //파이어스토어 유저 정보 업데이트 
            }
        }
    }

    private fun createAccount(Name:String, password:String,Email:String){
        //회원가입 성공을 알림.
        //로그인하고 메인액티비티 이동.
        auth?.createUserWithEmailAndPassword(Email,password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    enrollUserInformation(Name) //유저정보 업데이트 하고
                    MoveMainActivity(auth?.currentUser)
                    Toast.makeText(this, "회원가입 성공"+"\n"+"${FBAuth.getDisplayName()}"+"님 환영합니다.", Toast.LENGTH_SHORT).show()
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
package com.example.bankrecipe.ui.myPage

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.bankrecipe.MainActivity
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.FragmentMyPageBinding
import com.example.bankrecipe.ui.chat.ChatViewModel
import com.example.bankrecipe.ui.sign.SignIn
import com.google.firebase.auth.ktx.userProfileChangeRequest

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyPage
        myPageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        //https://firebase.google.com/docs/auth/android/manage-users?hl=ko 참고
        //val str = FBAuth.auth.currentUser?.uid //toString일 때 문제
        if (FBAuth.getDisplayName() == "null") { //사용자가 없을 때
            //toString의 경우 null을 "null"로 리던
            //로그인 부분 등장
            binding.statMessage.visibility = View.VISIBLE
            binding.btnLogout.visibility = View.GONE
            binding.btnLogout.text = "로그아웃"
            binding.textView3.text = "로그인이 필요합니다."
            binding.statMessage.text = "로그인"
            binding.btnEditProfile.visibility = View.GONE
        } else {
            binding.statMessage.visibility = View.GONE
            binding.btnLogout.visibility = View.VISIBLE
            binding.textView3.text = FBAuth.getDisplayName()
            binding.btnEditProfile.visibility = View.VISIBLE
            //binding.imageView2(기본 이미지)
            /*user!!.updateProfile(profileUpdates).addOnCompleteListener { task -> if(task.isSuccessful){
                Toast.makeText(this.context, "${FBAuth.auth.currentUser?.displayName.toString()}"+"님 환영합니다.ㅅ", Toast.LENGTH_SHORT).show()
            }
            }*/
        }
        val btnSignInFromMypage = binding.statMessage
        btnSignInFromMypage.setOnClickListener {
            val intent = Intent(activity, SignIn::class.java)
            startActivity(intent)
            //manifest에서 .은 현재 패키지를 의미함.
        }
        binding.btnLogout.setOnClickListener {
            binding.btnLogout.visibility = View.GONE
            binding.statMessage.visibility = View.VISIBLE
            binding.statMessage.text = "로그인"
            binding.textView3.text = "로그인이 필요합니다."
            FBAuth.auth.signOut()
        }
        
        binding.btnEditProfile.setOnClickListener { 
            //프로필 수정
        }

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
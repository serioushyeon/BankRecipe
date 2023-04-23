package com.example.bankrecipe.ui.myPage

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bankrecipe.databinding.FragmentMyPageBinding
import com.example.bankrecipe.ui.chat.ChatViewModel
import com.example.bankrecipe.ui.sign.SignIn

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
        val btnSignInFromMypage = binding.button
        btnSignInFromMypage.setOnClickListener {
            val intent = Intent(activity, SignIn::class.java)
            startActivity(intent)
            //manifest에서 .은 현재 패키지를 의미함.
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
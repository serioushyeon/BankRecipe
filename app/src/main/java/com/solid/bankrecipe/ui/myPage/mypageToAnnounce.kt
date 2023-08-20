package com.solid.bankrecipe.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solid.bankrecipe.R

class mypageToAnnounce : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_to_announce)
        val mainActionBar = supportActionBar
        mainActionBar!!.hide() //액션바 숨기기
    }
}
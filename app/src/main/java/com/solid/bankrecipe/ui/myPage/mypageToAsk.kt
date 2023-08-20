package com.solid.bankrecipe.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solid.bankrecipe.R

class mypageToAsk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_to_ask)
        val mainActionBar = supportActionBar
        mainActionBar!!.hide() //액션바 숨기기
    }
}
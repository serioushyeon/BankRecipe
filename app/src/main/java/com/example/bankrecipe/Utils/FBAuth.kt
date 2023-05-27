package com.example.bankrecipe.Utils

import com.google.firebase.auth.FirebaseAuth

object FBAuth { //유일객체 사용
    
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUid(): String{
        return auth.currentUser?.uid.toString()
    }
    fun getDisplayName() : String{
        return auth.currentUser?.displayName.toString()
    }
    fun setPassword(password: String){
        auth.currentUser?.updatePassword(password)
    }
}
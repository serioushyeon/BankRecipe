package com.example.bankrecipe.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityCommunityPostBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class CommunityPost : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostBinding
    lateinit var firestore: FirebaseFirestore
    private lateinit var key:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_community_post)
        setContentView(R.layout.activity_community_post)
        firestore = FirebaseFirestore.getInstance()
        key = intent.getStringExtra("key").toString()
        getData(key)
    }
    private fun getData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val dataModel = snapshot.getValue(CommunityData::class.java)
                    binding.postTitle.text = dataModel?.title
                    binding.postText2.text = dataModel?.make
                    binding.postText3.text = dataModel?.period
                    binding.postChat.text = dataModel?.subtext
                }catch (e:Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
    }
}
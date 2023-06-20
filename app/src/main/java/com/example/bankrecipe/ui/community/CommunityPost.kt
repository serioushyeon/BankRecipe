package com.example.bankrecipe.ui.community

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.annotation.GlideModule
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.Utils.FBRef
import com.example.bankrecipe.ui.map.MapData
import com.google.firebase.firestore.FirebaseFirestore
import com.rd.PageIndicatorView
import java.util.*

@GlideModule
class CommunityPost : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore

    lateinit var textWriter: TextView
    lateinit var imageIv: ImageView
    lateinit var Title: TextView
    lateinit var Subtext : TextView
    lateinit var make : TextView
    lateinit var period : TextView
    lateinit var time : TextView
    private lateinit var key: String
    lateinit var map : TextView
    lateinit var viewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this,R.layout.activity_community_post)
        setContentView(R.layout.activity_community_post)
        firestore = FirebaseFirestore.getInstance()
        key = intent.getStringExtra("key").toString()
        map = findViewById(R.id.post_address)
        val menu = findViewById<ImageView>(R.id.post_menu)
        textWriter = findViewById(R.id.post_name)
        //imageIv = findViewById(R.id.ivPostProfile)
        val itemList = ArrayList<CommunityData>() //리스트 아이템 배열
        val indicator = findViewById<PageIndicatorView>(R.id.indicator)

        val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicator.setSelected(position) // ViewPager 의 position 값이 변경된 경우 Indicator Position 변경
            }
        }
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = CommunityPostAdapter(itemList,key,this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.registerOnPageChangeCallback(pagerCallback) // 콜백 등록

        Title = findViewById(R.id.post_title)
        make = findViewById(R.id.post_text2)
        period = findViewById(R.id.post_text3)
        Subtext = findViewById(R.id.post_text4)
        time = findViewById(R.id.post_time)
        menu.setOnClickListener {
            showDialog()
        }

            val mykey = FBAuth.getUid()
            firestore.collection("photo").document(key).get().addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    Log.d("key값",key.toString())
                    var photo = task.result?.toObject(CommunityData::class.java)
                    indicator.setSelected(0) // 1번째 이미지가 선택된 것으로 초기화
                    indicator.count = itemList.size // 이미지 리스트 사이즈만큼 생성
                    indicator.visibility = View.VISIBLE

                    //Glide.with(this@CommunityPost).load(photo?.imageUri).into(imageIv)
                        //Log.d("이미지 uri ",photo?.imageUri.toString())
                    textWriter.text = photo?.id
                    Title.text = photo?.title
                    make.text = photo?.make
                    period.text = photo?.period
                    Subtext.text = photo?.subtext
                    time.text = FBRef.calculationTime(photo?.date!!.toLong())
                    val writerUid = photo?.uid
                    if(writerUid.equals(FBAuth.getUid())){
                    firestore.collection("map").document(FBAuth.getUid()).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var mapdata = task.result?.toObject(MapData::class.java)
                                val mapUid = mapdata?.mkey
                                map.text = mapdata?.mapaddress
                                Log.d("나의키", mykey)
                                Log.d("mapkey", mapUid.toString())


                            }
                        }
                        }else {
                        if (writerUid != null) {
                            firestore.collection("map").document(writerUid).get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        var mapdata = task.result?.toObject(MapData::class.java)
                                        val mapUid = mapdata?.mkey
                                        map.text = mapdata?.mapaddress


                                    }
                                }
                        }
                    }
                    if(mykey.equals(writerUid)) {
                        Log.d("나의키",mykey)
                        Log.d("작성자키",writerUid.toString())
                        menu.isVisible = true
                    }else {
                        //채팅하기버튼활성화
                        menu.isVisible = false
                    }
                }
            }


    }
    private fun initViewPager() {

    }
    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")
        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.deletebtn)?.setOnClickListener {
            firestore.collection("photo").document(key).delete().addOnCompleteListener {
                if(it.isSuccessful)
                    Toast.makeText(this,"삭제완료",Toast.LENGTH_LONG).show()

            }
            alertDialog.dismiss()
            finish()
        }
    }
}
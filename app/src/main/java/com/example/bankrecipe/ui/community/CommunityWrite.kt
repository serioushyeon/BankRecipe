package com.example.bankrecipe.ui.community

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.ActivityCommunityWriteBinding
import com.example.bankrecipe.ui.map.MapData
import com.example.bankrecipe.ui.recipe.RecipePostData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommunityWrite : AppCompatActivity() {
    private var uriList = ArrayList<Uri>()
    var abc = ArrayList<String>()
    private var finishImage : String? = null
    private val maxNumber = 10
    lateinit var adapter: CommunityWriteAdapter
    //val adapter = CommunityWriteAdapter(list,this)
    private var mapaddress : String? = null
    lateinit var imageIv : ImageView
    lateinit var textTitle : EditText
    lateinit var textPrice : EditText
    lateinit var textEt : EditText
    lateinit var textMake : EditText
    lateinit var textPeriods : EditText
    lateinit var countArea : TextView
    val IMAGE_PICK=1111
    lateinit var storage:FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)//앱에서 앨범에 접근을 허용할지 선택하는 메시지, 한 번 허용하면 앱이 설치돼 있는 동안 다시 뜨지 않음.
        var recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        storage = FirebaseStorage.getInstance()
        firestore=FirebaseFirestore.getInstance()
        imageIv = findViewById(R.id.write_camera)
        textTitle = findViewById(R.id.write_title)
        textPrice = findViewById(R.id.write_price)
        textEt = findViewById(R.id.write_contents)
        textMake = findViewById(R.id.write_make)
        textPeriods = findViewById(R.id.write_period)
        countArea = findViewById(R.id.countArea)
        adapter = CommunityWriteAdapter(uriList, this)
        //val layoutManager = LinearLayoutManager(this)
        val linearlayout = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);
        recyclerview.layoutManager = linearlayout
        recyclerview.adapter = adapter
        // ImageView를 클릭할 경우
        imageIv.setOnClickListener {

            if (uriList.count() == maxNumber) {
                    Toast.makeText(applicationContext, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return@setOnClickListener

            }
            var intent = Intent()
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.action = Intent.ACTION_PICK
            intent.type="image/*"
            startActivityForResult(intent,IMAGE_PICK)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //완료클릭
            R.id.community_action_btn -> {
                Toast.makeText(this,"완료클릭",Toast.LENGTH_SHORT).show()

                    for (i in 0 until uriList.count()) {
                        imageUpload(uriList.get(i), i)
                        var selectedImageUri = uriList?.get(i)
                           // Log.i("imageUri selectimage", "이미지 uri: $abc")

                        try {
                            Thread.sleep(500)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    //배열에 넣어줌


                    return true
                }
            }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.community_menu, menu) //상단바 메뉴
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_PICK&&resultCode== Activity.RESULT_OK){
            uriList.clear()
            val clipData = data?.clipData

            if(data?.clipData != null){ //사진 여러개 선택할 경우
                val clipDataSize = clipData?.itemCount
                val selectableCount = maxNumber - uriList.count()
                data.clipData.let { 
                    clipData ->
                    for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                        var selectedImageUri = clipData?.getItemAt(i)?.uri
                        if (selectedImageUri != null) {
                            uriList.add(selectedImageUri)
                          //  selectImage=data?.clipData!!.getItemAt(i).uri
                           //imageIv.setImageURI(data?.clipData!!.getItemAt(i).uri)
                        }

                        //TODO 얻어온 이미지 uri로 작업 진행

                    }
                }
                
                

            }
            adapter.notifyDataSetChanged()

            printCount()

            //selectImage=data?.data
           // imageIv.setImageURI(selectImage)
            //imageIv.setImageURI(data?.clipData!!.getItemAt(0).uri)
        }
    }
    private fun printCount() {
        val text = "${uriList.count()}/${maxNumber}"
        countArea.text = text
    }
    private fun getData(img: String){
        abc.add(img)
        if(abc.count()==uriList.count()){
            getCommunity(abc)
        }

    }
    private fun getCommunity(list : ArrayList<String>){
        val time = FBAuth.getTime()
        val ukey = FBAuth.getUid()
        val eid = FBAuth.getDisplayName()

        firestore.collection("map").document(FBAuth.getUid()).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var mapdata = task.result?.toObject(MapData::class.java)
                    mapaddress = mapdata!!.mapaddress
                    var photo =
                        CommunityData(
                            textTitle.text.toString(),
                            textPrice.text.toString(),
                            textMake.text.toString(),
                            textPeriods.text.toString(),
                            textEt.text.toString(),
                            list,
                            time,
                            eid,
                            ukey,
                            "",
                            mapaddress

                        )
                    firestore.collection("photo")
                        .document().set(photo)
                        .addOnSuccessListener {
                            finish()
                        }
                }
            }
        Log.i("mapaddress", "이미지 uri: ${mapaddress}")


    }
    private fun imageUpload(uri: Uri, count: Int) {
        val fileName = SimpleDateFormat("yyyyMMddHHmmss_${count}").format(Date())
        storage.getReference().child("image").child("${fileName}.png")
            .putFile(uri!!).addOnSuccessListener {
                    taskSnapshot ->
                //파일 업로드 성공
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        it ->

                    finishImage=it!!.toString()

                    Log.i("imageUri", "이미지 uri: ${finishImage}")
                    getData(finishImage!!)
                }

            }
    }
}



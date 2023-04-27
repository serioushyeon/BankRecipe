package com.example.bankrecipe.ui.community

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityCommunityWriteBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class CommunityWrite : AppCompatActivity() {
    lateinit var imageIv : ImageView
    lateinit var textTitle : EditText
    lateinit var textPrice : EditText
    lateinit var textEt : EditText
    lateinit var textMake : EditText
    lateinit var textPeriods : EditText
    private lateinit var binding : ActivityCommunityWriteBinding
    val IMAGE_PICK=1111
    var selectImage: Uri?=null
    lateinit var storage:FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)//앱에서 앨범에 접근을 허용할지 선택하는 메시지, 한 번 허용하면 앱이 설치돼 있는 동안 다시 뜨지 않음.
        storage = FirebaseStorage.getInstance()
        firestore=FirebaseFirestore.getInstance()
        imageIv = findViewById(R.id.write_camera)
        textTitle = findViewById(R.id.write_title)
        textPrice = findViewById(R.id.write_price)
        textEt = findViewById(R.id.write_contents)
        textMake = findViewById(R.id.write_make)
        textPeriods = findViewById(R.id.write_period)
        imageIv.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,IMAGE_PICK)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //완료클릭
            R.id.community_action_btn -> {
                Toast.makeText(this,"완료클릭",Toast.LENGTH_SHORT).show()
                if(selectImage!=null){
                    var fileName =
                        SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                    storage.getReference().child("image").child(fileName)
                        .putFile(selectImage!!)
                        .addOnSuccessListener {
                            taskSnapshot ->
                                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                                    it ->
                                    var imageUri=it.toString()
                                    var photo=
                                        CommunityData(
                                            textTitle.text.toString(),textPrice.text.toString(),textMake.text.toString(),
                                            textPeriods.text.toString(),textEt.text.toString(),imageUri)
                                    firestore.collection("photo")
                                        .document().set(photo)
                                        .addOnSuccessListener {
                                            finish()
                                        }
                                }

                        }
                }
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
            selectImage=data?.data
            imageIv.setImageURI(selectImage)
        }
    }
}
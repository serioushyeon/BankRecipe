package com.example.bankrecipe.ui.recipe

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.ActivityRecipePostRegisterBinding
import com.example.bankrecipe.ui.community.CommunityData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecipePostRegisterActivity : AppCompatActivity() {
    lateinit var imageIv : ImageView
    lateinit var textTitle : EditText
    lateinit var textPrice : EditText
    lateinit var textContent : EditText
    val IMAGE_PICK=1111
    var selectImage: Uri?=null
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityRecipePostRegisterBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var ingredient : ArrayList<RegisterIngredientData>
    lateinit var kcalList : ArrayList<RecipeKcalData>
    var kcal = 0.0F
    lateinit var adapter : RecipePostRegisterAdapter
    var ingStringList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance()
        firestore=FirebaseFirestore.getInstance()
        imageIv = binding.recipePostRegImg
        textTitle = binding.recipePostRegTitle
        textPrice = binding.recipePostRegPrice
        textContent = binding.recipePostRegContent
        imageIv.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,IMAGE_PICK)
        }

        ingredient = ArrayList<RegisterIngredientData>()
        kcalList = ArrayList<RecipeKcalData>()
        ingStringList = ArrayList<String>()
        for(item in ingredient){
            ingStringList.add(item.ingredient)
        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {//Result 매개변수 콜백 메서드
                //ActivityResultLauncher<T>에서 T를 intent로 설정했으므로
                //intent자료형을 Result 매개변수(콜백)를 통해 받아온다
                //엑티비티에서 데이터를 갖고왔을 때만 실행
                if (it.resultCode == RESULT_OK) {
                    val intent = it.data
                    ingredient = intent!!.getSerializableExtra("ingredient") as ArrayList<RegisterIngredientData>
                    ingStringList.clear()
                    for(item in ingredient){
                        ingStringList.add(item.ingredient)
                        var temp = LoadKcalData.getKcalItem().find { i -> i.name.equals(item.ingredient) }!!
                        kcalList.add(temp)
                        kcal += temp.kcal.toFloat()
                    }
                    adapter.notifyDataSetChanged()
                }
            }

        binding.recipePostRegIngBtn.setOnClickListener {
            val intent = Intent(applicationContext, RegisterIngredientActivity::class.java)
            intent.putExtra("flag", "post")
            activityResultLauncher.launch(intent)

        }
        adapter = RecipePostRegisterAdapter(this,ingStringList)
        adapter.notifyDataSetChanged()
        binding.recipePostRegIngRv.adapter = adapter
        binding.recipePostRegIngRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        binding.recipePostRegBtn.setOnClickListener {
            Toast.makeText(this,"등록클릭", Toast.LENGTH_SHORT).show()
            if(selectImage!=null){
                var fileName =
                    SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                storage.getReference().child("recipe").child(fileName)
                    .putFile(selectImage!!)
                    .addOnSuccessListener {
                            taskSnapshot ->
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                                it ->
                            var imageUri=it.toString()
                            var recipe=
                                RecipePostData(
                                    textTitle.text.toString(),ingStringList,
                                    textContent.text.toString(),imageUri, FBAuth.getDisplayName(),textPrice.text.toString(), fileName, kcal.toString())
                            firestore.collection("recipe")
                                .document().set(recipe)
                                .addOnSuccessListener {
                                    finish()
                                }
                        }

                    }
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_PICK&&resultCode== Activity.RESULT_OK){
            selectImage=data?.data
            imageIv.setImageURI(selectImage)
        }
    }
}
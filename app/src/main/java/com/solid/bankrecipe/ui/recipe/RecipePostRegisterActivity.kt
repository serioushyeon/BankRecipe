package com.solid.bankrecipe.ui.recipe

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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R
import com.solid.bankrecipe.Utils.FBAuth
import com.solid.bankrecipe.databinding.ActivityRecipePostRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecipePostRegisterActivity : AppCompatActivity() {
    private var uriList = ArrayList<Uri>()
    private var abc = ArrayList<String>()
    private val maxNumber = 10
    private var finishImage : String? = null
    lateinit var imageIv : ImageView
    lateinit var textTitle : EditText
    lateinit var textPrice : EditText
    lateinit var textContent : EditText
    val IMAGE_PICK=1111
   // var selectImage: Uri?=null
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityRecipePostRegisterBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var ingredient : ArrayList<RegisterIngredientData>
    lateinit var kcalList : ArrayList<RecipeKcalData>
    var kcal = 0.0F
    lateinit var imageadapter: RecipeImageAdapter
    lateinit var adapter : RecipePostRegisterAdapter
    var ingStringList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance()
        firestore=FirebaseFirestore.getInstance()
        imageIv = binding.writeCamera
        var recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        textTitle = binding.recipePostRegTitle
        textPrice = binding.recipePostRegPrice
        textContent = binding.recipePostRegContent

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

        imageadapter = RecipeImageAdapter(uriList, this)
        val linearlayout = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);
        binding.recyclerView.layoutManager = linearlayout
       binding.recyclerView.adapter = imageadapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //완료클릭
            R.id.recipe_action_btn -> {
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
        menuInflater.inflate(R.menu.recipe_menu, menu) //상단바 메뉴
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
           imageadapter.notifyDataSetChanged()

            printCount()

            //selectImage=data?.data
            // imageIv.setImageURI(selectImage)
            //imageIv.setImageURI(data?.clipData!!.getItemAt(0).uri)
        }
    }
    private fun getData(img: String){
        abc.add(img)
        if(abc.count()==uriList.count()){
            getRecipe(abc)
        }

    }
    private fun getRecipe(list : ArrayList<String>) {
        val time = FBAuth.getTime()
        val ukey = FBAuth.getUid()
        val eid = FBAuth.getDisplayName()
        var recipe =
            RecipePostData(
                textTitle.text.toString(),
                ingStringList,
                textContent.text.toString(),
                list,
                FBAuth.getDisplayName(),
                textPrice.text.toString(),
                time,
                kcal.toString()
            )
        firestore.collection("recipe")
            .document().set(recipe)
            .addOnSuccessListener {
                finish()
            }
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

    private fun printCount() {
        val text = "${uriList.count()}/${maxNumber}"
        binding.countArea.text = text
    }
}
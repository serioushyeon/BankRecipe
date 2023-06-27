package com.solid.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.solid.bankrecipe.R
import com.solid.bankrecipe.databinding.ActivityRecipePostDetailBinding
import com.solid.bankrecipe.ui.community.CommunityPostAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.rd.PageIndicatorView

class RecipePostDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipePostDetailBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var adapter : RecipePostRegisterAdapter
    lateinit var imageadapter: RecipeImagePostAdapter
    private var img =  arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val key = intent.getStringExtra("item")
        firestore = FirebaseFirestore.getInstance()
        var indicator = findViewById<PageIndicatorView>(R.id.indicator)
        val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicator.setSelected(position) // ViewPager 의 position 값이 변경된 경우 Indicator Position 변경

            }
        }
        if (key!=null){

            firestore.collection("recipe").document(key).get().addOnCompleteListener {

                    task ->
                if(task.isSuccessful){
                    Log.d("key값",key.toString())
                    var recipe = task.result?.toObject(RecipePostData::class.java)
                    img = recipe?.imageUri!!
                   // Glide.with(this@RecipePostDetailActivity).load(recipe?.img).into(binding.recipePostDetailImg)
                   // Log.d("이미지 uri null",recipe?.img.toString())

                    imageadapter = RecipeImagePostAdapter(recipe?.imageUri!!, this)
                    binding.viewPager.adapter = imageadapter
                    binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    binding.viewPager.registerOnPageChangeCallback(pagerCallback) // 콜백 등록
                   imageadapter.notifyDataSetChanged()
                    binding.indicator.setSelected(0) // 1번째 이미지가 선택된 것으로 초기화
                    binding.indicator.count = recipe?.imageUri!!.size // 이미지 리스트 사이즈만큼 생성
                    Log.d("indicator", indicator?.count.toString())
                    indicator.visibility = View.VISIBLE
                    binding.recipePostDetailContent.text = recipe?.content
                    binding.recipePostDetailTitle.text = recipe?.title
                    adapter = RecipePostRegisterAdapter(this,recipe!!.ingredient)
                    adapter.notifyDataSetChanged()
                    binding.recipePostDetailRv.adapter = adapter
                    binding.recipePostDetailRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
                }
            }
        }
    }
}
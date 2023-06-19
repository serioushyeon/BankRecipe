package com.example.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.databinding.ActivityRecipePostDetailBinding
import com.example.bankrecipe.ui.community.CommunityData
import com.google.firebase.firestore.FirebaseFirestore

class RecipePostDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipePostDetailBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var adapter : RecipePostRegisterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val key = intent.getStringExtra("item")
        firestore = FirebaseFirestore.getInstance()

        if (key!=null){

            firestore.collection("recipe").document(key).get().addOnCompleteListener {

                    task ->
                if(task.isSuccessful){
                    Log.d("key값",key.toString())
                    var recipe = task.result?.toObject(RecipePostData::class.java)
                    Glide.with(this@RecipePostDetailActivity).load(recipe?.img).into(binding.recipePostDetailImg)
                    Log.d("이미지 uri null",recipe?.img.toString())
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
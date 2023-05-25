package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.databinding.ActivityRecipeDetailBinding
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.streams.toList


class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val getitem = intent.getSerializableExtra("item") as RecipeData
        var ingredientList = LoadRecipeData.getIngItem()
        var explainList = LoadRecipeData.getExItem()
        var recipeIngredientList = ingredientList.stream().filter { item -> getitem.num.equals(item.num)}
        var recipeExplainList = explainList.stream().filter { item -> getitem.num.equals(item.num) }

        Glide.with(this).load(getitem.imgUrl).into(binding.imageView)
        binding.recipeDetailTitle.text = getitem.title

        val recipeExplainAdapter = RecipeDetailExplainAdapter(this, recipeExplainList.toList() as ArrayList<RecipeDetailExplainData>)
        recipeExplainAdapter.notifyDataSetChanged()
        binding.recipeDetailExplainRv.adapter = recipeExplainAdapter
        binding.recipeDetailExplainRv.layoutManager = LinearLayoutManager(this)

        val recipeIngAdapter = RecipeDetailIngredientAdapter(this, recipeIngredientList.toList() as ArrayList<RecipeDetaliIngredientData>)
        recipeIngAdapter.notifyDataSetChanged()
        binding.recipeDetailIngRv.adapter = recipeIngAdapter
        binding.recipeDetailIngRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) //수평 레이아웃

    }

}
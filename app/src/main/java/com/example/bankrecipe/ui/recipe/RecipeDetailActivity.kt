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
        var ingredientList = loadIngData()
        var explainList = loadExData()
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
    private fun loadIngData(): ArrayList<RecipeDetaliIngredientData> {
        var itemList = ArrayList<RecipeDetaliIngredientData>()
        val assetManager = this.assets
        val inputStream: InputStream = assetManager.open("recipe_ingredient.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream)) //UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            itemList.add(RecipeDetaliIngredientData(content[0], content[2], content[3], content[6]))
        }
        return itemList
    }
    private fun loadExData(): ArrayList<RecipeDetailExplainData> {
        var itemList = ArrayList<RecipeDetailExplainData>()
        val assetManager = this.assets
        val inputStream: InputStream = assetManager.open("recipe_explain.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream))//UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            itemList.add(RecipeDetailExplainData(content[0], content[1], content[2]))
        }
        return itemList
    }

}
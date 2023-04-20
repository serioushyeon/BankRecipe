package com.example.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.databinding.ActivityRecipeIngredientPriceBinding

class RecipeIngredientPriceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeIngredientPriceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeIngredientPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "물가 정보"
        val getitem = intent.getSerializableExtra("item") as ArrayList<RecipeIngredientPriceData>
        val recipeIngredientPriceAdapter = RecipeIngredientPriceAdapter(this, getitem)
        recipeIngredientPriceAdapter.notifyDataSetChanged()
        binding.recipeIngredientPriceRv.adapter = recipeIngredientPriceAdapter
        binding.recipeIngredientPriceRv.layoutManager = LinearLayoutManager(this)
    }
}
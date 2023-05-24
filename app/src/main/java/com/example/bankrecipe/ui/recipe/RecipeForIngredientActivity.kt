package com.example.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankrecipe.databinding.ActivityRecipeForIngredientBinding

class RecipeForIngredientActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeForIngredientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeForIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
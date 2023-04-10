package com.example.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityBudgetBinding
import com.example.bankrecipe.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}
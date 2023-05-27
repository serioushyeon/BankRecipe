package com.example.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityRecipePostRegisterBinding

class RecipePostRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipePostRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recipePostRegBtn.setOnClickListener {
        }

    }
}
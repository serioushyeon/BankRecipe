package com.example.bankrecipe.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityMapSearchBinding
import com.example.bankrecipe.databinding.ActivityRecipeForIngredientBinding

class MapSearch : AppCompatActivity() {
    private lateinit var binding : ActivityMapSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBarInputView.setOnClickListener {

        }
    }
}
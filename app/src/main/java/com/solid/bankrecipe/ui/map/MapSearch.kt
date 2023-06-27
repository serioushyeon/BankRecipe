package com.solid.bankrecipe.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.solid.bankrecipe.R
import com.solid.bankrecipe.databinding.ActivityMapSearchBinding
import com.solid.bankrecipe.databinding.ActivityRecipeForIngredientBinding

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
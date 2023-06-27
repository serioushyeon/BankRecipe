package com.solid.bankrecipe.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.solid.bankrecipe.databinding.ActivityBudgetBinding

class BudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBudgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "예산에 맞는 레시피"
        val itemList = intent.getSerializableExtra("itemlist") as ArrayList<RecipeData>
        val recipeAdapter = RecipeAdapter(this, itemList)
        recipeAdapter.notifyDataSetChanged()

        binding.budgetRecyclerview.adapter = recipeAdapter
        binding.budgetRecyclerview.layoutManager = LinearLayoutManager(this)

    }
}
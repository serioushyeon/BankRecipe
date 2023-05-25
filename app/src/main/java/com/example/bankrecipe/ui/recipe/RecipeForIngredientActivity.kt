package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.databinding.ActivityRecipeForIngredientBinding
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.streams.toList

class RecipeForIngredientActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeForIngredientBinding
    lateinit var selectIngItem : ArrayList<RegisterIngredientData>
    lateinit var adapter: RecipeForIngredientAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeForIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectIngItem = intent.getSerializableExtra("item") as ArrayList<RegisterIngredientData>

        var correctRecipe : MutableMap<String, Int> = HashMap()
        val ingitem =LoadRecipeData.getIngItem()
        var count = 0
        for(item in ingitem){
            for(sitem in selectIngItem){
                if(sitem.ingredient == item.category) {
                    if (correctRecipe.containsKey(item.num))
                        correctRecipe.put(item.num, correctRecipe.get(item.num)!! + 1)
                    else
                        correctRecipe[item.num] = 1
                }
            }
        }

        //correctRecipe = correctRecipe.toList().sortedByDescending { it.second }.toMap() as MutableMap<String, Int>
        var cr = LoadRecipeData.getGeneralItem.stream().filter { item -> correctRecipe.containsKey(item.num) }.toList() as ArrayList<RecipeData>
        var correctRecipes : MutableMap<RecipeData, Int> = HashMap()
        for(item in cr){
            correctRecipes[item] = correctRecipe[item.num]!!
        }

        correctRecipes = correctRecipes.toList().sortedByDescending { it.second }.toMap() as MutableMap<RecipeData, Int>
        cr = correctRecipes.keys.toList() as ArrayList<RecipeData>


        adapter = RecipeForIngredientAdapter(this, cr,
            correctRecipe as HashMap<String, Int>
        )
        adapter.notifyDataSetChanged()
        binding.recForIngRv.adapter = adapter
        binding.recForIngRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) //수평 레이아웃

    }
}

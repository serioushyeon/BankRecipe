package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bankrecipe.databinding.ActivityRecipeForIngredientBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.streams.toList

class RecipeForIngredientActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeForIngredientBinding
    lateinit var selectIngItem : ArrayList<RegisterIngredientData>
    lateinit var adapter: RecipeForIngredientAdapter
    var correctRecipe : MutableMap<String, Int> = HashMap()
    var correctRecipes : MutableMap<RecipeData, Int> = HashMap()
    var correctPost : MutableMap<String, Int> = HashMap()
    lateinit var firestore: FirebaseFirestore
    lateinit var cr : ArrayList<RecipeData>
    var itemList = ArrayList<RecipePostData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeForIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var getItemList = LoadRecipeData.getGeneralItem()
        selectIngItem = intent.getSerializableExtra("item") as ArrayList<RegisterIngredientData>
        val ingitem =LoadRecipeData.getIngItem()

        for(item in ingitem){
            for(sitem in selectIngItem){
                if(sitem.ingredient == item.ingredient) {
                    if (correctRecipe.containsKey(item.num))
                        correctRecipe.put(item.num, correctRecipe.get(item.num)!! + 1)
                    else
                        correctRecipe[item.num] = 1
                }
            }
        }

        //correctRecipe = correctRecipe.toList().sortedByDescending { it.second }.toMap() as MutableMap<String, Int>
        cr = LoadRecipeData.getGeneralItem.stream().filter { item -> correctRecipe.containsKey(item.num) }.toList() as ArrayList<RecipeData>
        for(item in cr){
            correctRecipes[item] = correctRecipe[item.num]!!
        }

        if(correctRecipes.isNotEmpty()){
            correctRecipes = correctRecipes.toList().sortedByDescending { it.second }.toMap() as MutableMap<RecipeData, Int>
            cr = correctRecipes.keys.toList() as ArrayList<RecipeData>
        }
        else
        {
            cr = ArrayList<RecipeData>()
        }

        for(post in itemList) {
            for (pIng in post.ingredient) {
                for (sitem in selectIngItem) {
                    if (sitem.ingredient == pIng) {
                        if (correctPost.containsKey(post.uid))
                            correctPost.put(post.uid, correctPost.get(post.uid)!! + 1)
                        else
                            correctPost[post.uid] = 1
                    }
                }
            }
        }
        initViewPager(getItemList)
    }
    private fun initViewPager(itemList: ArrayList<RecipeData>) {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = RecipeViewPager2Adapter(this)
        viewPager2Adapter.addFragment(RecipeForIngredientBasicFragment(cr, correctRecipe))
        viewPager2Adapter.addFragment(RecipeForIngredientUserFragment(selectIngItem)) //수정, 프래그먼트 만들기


        //Adapter 연결
        binding.recipeForIngredientVP.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.recipeForIngredientTab, binding.recipeForIngredientVP) { tab, position ->
            when (position) {
                0 -> tab.text = "기본 레시피"
                1 -> tab.text = "우리들의 레시피"
            }
        }.attach()
    }
}

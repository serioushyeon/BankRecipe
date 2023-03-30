package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding

class RecipeTabFragment : Fragment() {
    private var _binding: FragmentRecipeTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentRecipeTabBinding.inflate(inflater, container, false)
        val itemList = ArrayList<RecipeData>()

        itemList.add(RecipeData("레시피명1", "내용1", "00분", "난이도", "000kcal"))
        itemList.add(RecipeData("레시피명2", "내용2", "00분", "난이도", "000kcal"))
        itemList.add(RecipeData("레시피명3", "내용3", "00분", "난이도", "000kcal"))
        itemList.add(RecipeData("레시피명4", "내용4", "00분", "난이도", "000kcal"))
        itemList.add(RecipeData("레시피명5", "내용5", "00분", "난이도", "000kcal"))
        itemList.add(RecipeData("레시피명6", "내용6", "00분", "난이도", "000kcal"))

        val recipeAdapter = RecipeAdapter(itemList)
        recipeAdapter.notifyDataSetChanged()

        binding.recipeTabRecyclerview.adapter = recipeAdapter
        binding.recipeTabRecyclerview.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

}
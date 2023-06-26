package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentRecipeForIngredientBasicBinding

class RecipeForIngredientBasicFragment(val cr: ArrayList<RecipeData>,
                                       val correctRecipe: MutableMap<String, Int> ) : Fragment() {
    private var _binding: FragmentRecipeForIngredientBasicBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: RecipeForIngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeForIngredientBasicBinding.inflate(inflater, container, false)
        adapter = RecipeForIngredientAdapter(this.context, cr,
            correctRecipe as HashMap<String, Int>
        )
        adapter.notifyDataSetChanged()
        binding.recipeForIngredientBasicRV.adapter = adapter
        binding.recipeForIngredientBasicRV.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

}
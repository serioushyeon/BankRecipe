package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.databinding.FragmentRecipeForIngredientUserBinding

class RecipeForIngredientUserFragment(
    val sItem : ArrayList<RegisterIngredientData>
) : Fragment() {
    private var _binding: FragmentRecipeForIngredientUserBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: RecipeForIngredientUserAdapter
    var itemList = ArrayList<RecipePostData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeForIngredientUserBinding.inflate(inflater, container, false)
        adapter = RecipeForIngredientUserAdapter(this.context, sItem, itemList)
        adapter.notifyDataSetChanged()
        binding.recipeForIngredientUserRV.adapter = adapter
        binding.recipeForIngredientUserRV.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

}
package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.streams.toList

class RecipeTabFragment(var itemLists: ArrayList<RecipeData>, val category : String) : Fragment() {
    private var _binding: FragmentRecipeTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentRecipeTabBinding.inflate(inflater, container, false)

        var itemList = itemLists.stream().filter { item -> item.category.equals(category) }
        val recipeAdapter = RecipeAdapter(this.context, itemList.toList() as ArrayList<RecipeData>)
        recipeAdapter.notifyDataSetChanged()

        binding.recipeTabRecyclerview.adapter = recipeAdapter
        binding.recipeTabRecyclerview.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }
}
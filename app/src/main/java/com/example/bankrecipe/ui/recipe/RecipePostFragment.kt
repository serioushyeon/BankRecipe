package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentRecipePostBinding
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding

class RecipePostFragment : Fragment() {
    private var _binding: FragmentRecipePostBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentRecipePostBinding.inflate(inflater, container, false)

        //val recipeAdapter = RecipeAdapter(this.context, itemLists)
        //recipeAdapter.notifyDataSetChanged()

        //binding.recipeTabRecyclerview.adapter = recipeAdapter
        //binding.recipeTabRecyclerview.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }
}
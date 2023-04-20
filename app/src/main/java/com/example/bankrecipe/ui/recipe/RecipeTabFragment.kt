package com.example.bankrecipe.ui.recipe

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.streams.toList

class RecipeTabFragment(var itemLists: ArrayList<RecipeData>) : Fragment() {
    private var _binding: FragmentRecipeTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentRecipeTabBinding.inflate(inflater, container, false)

        //var itemList = itemLists.stream().filter { item -> item.category.equals(category) }
        var itemList = ArrayList<RecipeData>()
        var recipeAdapter = RecipeAdapter(this.context, itemLists)
        var btnSelect = binding.recipeTabKoBtn

        binding.recipeTabKoBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("한식") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            if(btnSelect != binding.recipeTabKoBtn)
                btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabKoBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabKoBtn
        }
        binding.recipeTabChBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("중국") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabChBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabChBtn
        }
        binding.recipeTabJapBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("일본") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabJapBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabJapBtn
        }
        binding.recipeTabWesBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("서양") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabWesBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabWesBtn
        }
        binding.recipeTabSaBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("동남아시아") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabSaBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabSaBtn
        }
        binding.recipeTabItBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("이탈리아") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabItBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabItBtn
        }
        binding.recipeTabFuBtn.setOnClickListener{
            itemList = itemLists.stream().filter { item -> item.category.equals("퓨전") }.toList() as ArrayList<RecipeData>
            recipeAdapter = RecipeAdapter(this.context, itemList)
            recipeAdapter.notifyDataSetChanged()
            binding.recipeTabRecyclerview.adapter = recipeAdapter
            btnSelect.setTextColor(Color.BLACK)
            binding.recipeTabFuBtn.setTextColor(resources.getColor(R.color.main_color))
            btnSelect = binding.recipeTabFuBtn
        }

        binding.recipeTabKoBtn.performClick()
        binding.recipeTabRecyclerview.adapter = recipeAdapter
        binding.recipeTabRecyclerview.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }
}
package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.bankrecipe.databinding.FragmentRecipeBinding
import com.google.android.material.tabs.TabLayoutMediator

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViewPager()

        return root
    }
    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = RecipeViewPager2Adapter(this.requireActivity())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())
        viewPager2Adatper.addFragment(RecipeTabFragment())


        //Adapter 연결
        binding.recipeViewpager.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.recipeTab, binding.recipeViewpager) { tab, position ->
            Log.e("RecipeTab", "ViewPager position: ${position}")
            when (position) {
                0 -> tab.text = "한식"
                1 -> tab.text = "양식"
                2 -> tab.text = "중식"
                3 -> tab.text = "일식"
                4 -> tab.text = "퓨전"
                5 -> tab.text = "후식"
                6 -> tab.text = "밑반찬"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
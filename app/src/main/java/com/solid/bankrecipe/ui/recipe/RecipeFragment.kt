package com.solid.bankrecipe.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.solid.bankrecipe.databinding.FragmentRecipeBinding
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

        var getItemList = LoadRecipeData.getGeneralItem()
        initViewPager(getItemList)

        return root
    }

    private fun initViewPager(itemList: ArrayList<RecipeData>) {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = RecipeViewPager2Adapter(this.requireActivity())
        /*viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "한식"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "서양"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "중국"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "일본"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "동남아시아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "이탈리아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "퓨전"))*/
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList))
        viewPager2Adapter.addFragment(RecipePostFragment())


        //Adapter 연결
        binding.recipeViewpager.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.recipeTab, binding.recipeViewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "기본 레시피"
                1 -> tab.text = "우리들의 레시피"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
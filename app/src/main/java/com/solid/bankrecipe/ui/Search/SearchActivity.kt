package com.solid.bankrecipe.ui.Search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.solid.bankrecipe.R
import com.solid.bankrecipe.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }
    private fun initViewPager() {
        val viewPagerAdapter = SearchViewPagerAdapter(this)
        viewPagerAdapter.addFragment(RecipeSearchFragment())
        viewPagerAdapter.addFragment(SellSearchFragment())
        binding.searchViewpager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.searchTab, binding.searchViewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "레시피"
                1 -> tab.text = "동네 거래"
            }
        }.attach()
    }
}
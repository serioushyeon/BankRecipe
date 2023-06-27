package com.solid.bankrecipe.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solid.bankrecipe.databinding.ActivityMyCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyCommunity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCommunityBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //var getItemList = loadData()
        initViewPager()
    }
    private fun initViewPager() {
        val viewPager2Adapter = CommunityViewPager2Adapter(this)
        viewPager2Adapter.addFragment(MyCommunityTabFragment())
        viewPager2Adapter.addFragment(MyCommunityFinishFragment())
        binding.mysellViewpager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.mysellTab, binding.mysellViewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "거래 중"
                1 -> tab.text = "거래 완료"
            }
        }.attach()
    }

}
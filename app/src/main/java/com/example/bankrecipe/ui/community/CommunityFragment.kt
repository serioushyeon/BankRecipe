package com.example.bankrecipe.ui.community

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.bankrecipe.databinding.FragmentCommunityBinding
import com.example.bankrecipe.databinding.FragmentRecipeBinding
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding
import com.example.bankrecipe.ui.recipe.RecipeAdapter
import com.example.bankrecipe.ui.recipe.RecipeData
import com.example.bankrecipe.ui.recipe.RecipeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    lateinit var firestore: FirebaseFirestore
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

        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initViewPager()

        return root
    }
    private fun initViewPager() {
        val viewPager2Adapter = CommunityViewPager2Adapter(this.requireActivity())
        viewPager2Adapter.addFragment(CommunityTabFragment())
        viewPager2Adapter.addFragment(CommunityListFragment())
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
                0 -> tab.text = "동네 거래"
                1 -> tab.text = "동네 가게"
            }
        }.attach()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
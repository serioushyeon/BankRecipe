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
import com.bumptech.glide.Glide
import com.example.bankrecipe.databinding.FragmentCommunityBinding
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding
import com.example.bankrecipe.ui.recipe.RecipeAdapter
import com.example.bankrecipe.ui.recipe.RecipeData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    lateinit var firestore: FirebaseFirestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        val itemList = ArrayList<CommunityData>() //리스트 아이템 배열


       binding.fabMain.setOnClickListener {
            startActivity(Intent(context,CommunityWrite::class.java))
        }
        /*itemList.add(CommunityData("알배추", "4000원"))
        itemList.add(CommunityData("사과","2000원"))
        itemList.add(CommunityData("오렌지","2000원"))
        itemList.add(CommunityData("바나나","2000원"))
        itemList.add(CommunityData("키위","2000원"))
        itemList.add(CommunityData("브로콜리","2000원"))*/
        val CommunityAdapter = CommunityAdapter(itemList)
        CommunityAdapter.notifyDataSetChanged()
        binding.communityTabRecyclerview.adapter = CommunityAdapter
        val gridLayoutManager = GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
        binding.communityTabRecyclerview.layoutManager = gridLayoutManager
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
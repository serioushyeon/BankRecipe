package com.example.bankrecipe.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentMyCommunityTabBinding
import com.example.bankrecipe.databinding.FragmentRecipeTabBinding
import com.google.firebase.firestore.FirebaseFirestore


class MyCommunityTabFragment: Fragment() {
    private var _binding: FragmentMyCommunityTabBinding?= null
    lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentMyCommunityTabBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        val itemList = ArrayList<CommunityData>()
        val MyCommunityAdapter = MyCommunityAdapter(itemList)
        MyCommunityAdapter.notifyDataSetChanged()
        binding.communityTabRecyclerview.adapter = MyCommunityAdapter

        val gridLayoutManager = GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)
        binding.communityTabRecyclerview.layoutManager = gridLayoutManager

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
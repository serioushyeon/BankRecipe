package com.solid.bankrecipe.ui.Search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.solid.bankrecipe.R
import com.solid.bankrecipe.databinding.FragmentCommunityTabBinding
import com.solid.bankrecipe.databinding.FragmentSellSearchBinding
import com.solid.bankrecipe.ui.community.CommunityData

class SellSearchFragment : Fragment() {
    private var _binding: FragmentSellSearchBinding?= null
    lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentSellSearchBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        val itemList = ArrayList<CommunityData>()

        val SearchAdapter = SearchAdapter(itemList)
        SearchAdapter.notifyDataSetChanged()
        binding.searchTabRecyclerview.adapter = SearchAdapter
        val gridLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.searchTabRecyclerview.layoutManager = gridLayoutManager
        //검색editetext 변화시

        binding.searchBarInputButton.setOnClickListener {
                //제목이 같은글자가 속해있을시 recyclerview 나오기

        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
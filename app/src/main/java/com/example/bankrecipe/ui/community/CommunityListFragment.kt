package com.example.bankrecipe.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentCommunityListBinding
import com.example.bankrecipe.databinding.FragmentCommunityTabBinding
import com.google.firebase.firestore.FirebaseFirestore


class CommunityListFragment : Fragment() {
    private var _binding: FragmentCommunityListBinding?= null
    lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentCommunityListBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        val itemList = ArrayList<SellerData>() //리스트 아이템 배열
        itemList.add(SellerData("칠칠이네","반찬과 각종 김치를 판매하고 있어요!!","오전10:00~오후 18:00"))
        itemList.add(SellerData("복순이네","계절과일과 과즙주스를 판매하고 있어요!!","오전10:00~오후 20:00"))
        itemList.add(SellerData("솔솔이네","각종 소스를 판매하고 있어요!!","오전07:00~오후 18:00"))

        val CommunityListAdapter = CommunityListAdapter(itemList)
        binding.communityListRecyclerview.adapter = CommunityListAdapter
        CommunityListAdapter.notifyDataSetChanged()
        val gridLayoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        binding.communityListRecyclerview.layoutManager = gridLayoutManager
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
    }
}
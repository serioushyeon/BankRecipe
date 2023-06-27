package com.example.bankrecipe.ui.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.FragmentCommunityBinding
import com.example.bankrecipe.databinding.FragmentCommunityTabBinding
import com.example.bankrecipe.databinding.FragmentMyCommunityTabBinding
import com.example.bankrecipe.ui.map.MapData
import com.google.firebase.firestore.FirebaseFirestore


class CommunityTabFragment : Fragment() {
    private var _binding: FragmentCommunityTabBinding?= null
    lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentCommunityTabBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        val itemList = ArrayList<CommunityData>() //리스트 아이템 배열


        binding.fabMain.setOnClickListener {
            startActivity(Intent(context, CommunityWrite::class.java))
        }
        if(firestore.collection("map").document(FBAuth.getUid()) !=null) {
            firestore.collection("map").document(FBAuth.getUid()).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var mapdata = task.result?.toObject(MapData::class.java)
                        var mapaddress = mapdata!!.mapaddress
                        val CommunityAdapter = CommunityAdapter(itemList, mapaddress)
                        CommunityAdapter.notifyDataSetChanged()
                        binding.communityTabRecyclerview.adapter = CommunityAdapter
                        val gridLayoutManager =
                            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                        binding.communityTabRecyclerview.layoutManager = gridLayoutManager
                    }
                }
        }else {
            Toast.makeText(requireContext(),"로그인을 해주세요 ",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
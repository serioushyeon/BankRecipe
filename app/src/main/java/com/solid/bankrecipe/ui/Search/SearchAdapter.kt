package com.solid.bankrecipe.ui.Search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.solid.bankrecipe.R
import com.solid.bankrecipe.Utils.FBAuth
import com.solid.bankrecipe.ui.community.CommunityAdapter
import com.solid.bankrecipe.ui.community.CommunityData
import com.solid.bankrecipe.ui.community.MyCommunityAdapter

class SearchAdapter(val itemList : ArrayList<CommunityData>):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("photo")?.addSnapshotListener{
                querySnapshot,firebaseFirestoreException ->
            itemList.clear()
            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(CommunityData::class.java)
                itemList.add(item!!)
                keyList.add(snapshot.id)
            }

            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_list_item, parent, false)

        return SearchViewHolder(view)
    }
    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val context = holder.itemView.context
        val imView = itemList[position].imageUri?.get(0).toString()
        holder.community_title.text = itemList[position].title
        holder.community_sub.text= itemList[position].price + "원"
        holder.itemView.setOnClickListener{
           // onClick(context,position)
        }
        Glide.with(holder.itemView.context).load(imView).into(holder.community_img)
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var community_img = itemView.findViewById<ImageView>(R.id.item_image)
        val community_title = itemView.findViewById<TextView>(R.id.item_title)
        val community_sub = itemView.findViewById<TextView>(R.id.item_sub)

    }
}

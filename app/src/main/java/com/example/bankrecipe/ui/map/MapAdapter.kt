package com.example.bankrecipe.ui.map

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.R
import com.google.firebase.firestore.FirebaseFirestore

class MapAdapter(val itemList : ArrayList<SearchResultData>) :
    RecyclerView.Adapter<MapAdapter.MapSearchViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("photo")?.addSnapshotListener{
                querySnapshot,firebaseFirestoreException ->
            itemList.clear()
            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(SearchResultData::class.java)
                itemList.add(item!!)
                keyList.add(snapshot.id)
            }

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapAdapter.MapSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_list_item, parent, false)

        return MapSearchViewHolder(view)
    }
    override fun onBindViewHolder(holder: MapAdapter.MapSearchViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.map_title.text = itemList[position].fullAddress
        holder.map_subtitle.text= itemList[position].name
        holder.itemView.setOnClickListener{
            onClick(context,position)
        }
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class MapSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val map_title = itemView.findViewById<TextView>(R.id.title)
        val map_subtitle = itemView.findViewById<TextView>(R.id.subtitle)
    }

    fun onClick(context: Context, position: Int) {

    }

}
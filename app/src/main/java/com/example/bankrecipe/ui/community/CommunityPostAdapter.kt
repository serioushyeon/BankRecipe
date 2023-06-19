package com.example.bankrecipe.ui.community

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.Write

class CommunityPostAdapter(val itemList : ArrayList<CommunityData>, val context: Context) :

    RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {
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



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_image_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommunityPostAdapter.ViewHolder, position: Int) {
       val imview =  itemList[position].imageUri?.toString()
        Glide.with(holder.itemView.context).load(imview).fitCenter().into(holder.imageArea)
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val imageArea = itemView.findViewById<ImageView>(R.id.image)

    }



}
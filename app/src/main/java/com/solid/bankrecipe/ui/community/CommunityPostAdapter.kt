package com.solid.bankrecipe.ui.community

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.solid.bankrecipe.R
import com.solid.bankrecipe.ui.recipe.RecipeImagePostAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class CommunityPostAdapter(var itemList: ArrayList<String>, private var context: Context) :

    RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    private lateinit var imView: String
    private var img =  arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_image_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommunityPostAdapter.ViewHolder, position: Int) {

        holder.bindItems(itemList[position])
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: String) {
            val imageArea = itemView.findViewById<ImageView>(R.id.image)
            Glide.with(context).load(item).fitCenter()
                .into(imageArea)
            Log.d("이미지 uri ", item)
        }

    }



}
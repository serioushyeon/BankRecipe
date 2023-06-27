package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solid.bankrecipe.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.Write

class RecipeImageAdapter(val itemList: ArrayList<Uri>, val context: Context) :
    RecyclerView.Adapter<RecipeImageAdapter.ViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_image_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecipeImageAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemList[position])
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Uri) {
            val imageArea = itemView.findViewById<ImageView>(R.id.image)
            Glide.with(context).load(item).into(imageArea)
        }
    }



}
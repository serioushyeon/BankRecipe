package com.example.bankrecipe.ui.community

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.ui.map.MapData
import com.example.bankrecipe.ui.recipe.RecipeAdapter
import com.example.bankrecipe.ui.recipe.RecipeData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Document
import org.w3c.dom.Text

class CommunityListAdapter(val itemList : ArrayList<SellerData>) :
    RecyclerView.Adapter<CommunityListAdapter.CommunityViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityListAdapter.CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)

        return CommunityViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommunityListAdapter.CommunityViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.store_name.text = itemList[position].name
        holder.store_introduce.text = itemList[position].introduce
        holder.store_time.text = itemList[position].storetime
    }
    override fun getItemCount(): Int {

        return itemList.count()
    }
    inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var store_name = itemView.findViewById<TextView>(R.id.store_title)
        val store_introduce = itemView.findViewById<TextView>(R.id.store_description)
        val store_time = itemView.findViewById<TextView>(R.id.store_time)

    }

}
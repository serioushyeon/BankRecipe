package com.example.bankrecipe.ui.community

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
import com.example.bankrecipe.ui.recipe.RecipeAdapter
import com.example.bankrecipe.ui.recipe.RecipeData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Document
import org.w3c.dom.Text

class CommunityAdapter(val itemList : ArrayList<CommunityData>) :
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_list_item, parent, false)

        return CommunityViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommunityAdapter.CommunityViewHolder, position: Int) {
        val context = holder.itemView.context
        val imView = itemList.get(position).imageUri
       // val date = itemList[position].date
       holder.community_title.text = itemList[position].title
        holder.community_sub.text= itemList[position].price + "원"
        holder.itemView.setOnClickListener{
            onClick(context,position)
        }
        //Glide.with(holder.itemView.context).load(itemList[position].imageUri).into(holder.community_img)
        Glide.with(holder.itemView.context).load(imView).into(holder.community_img)
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var community_img = itemView.findViewById<ImageView>(R.id.item_image)
        val community_title = itemView.findViewById<TextView>(R.id.item_title)
        //val community_price = itemView.findViewById<TextView>(R.id.write_price)
        //val community_make = itemView.findViewById<TextView>(R.id.write_make)
        //val community_periods = itemView.findViewById<TextView>(R.id.write_period)
        val community_sub = itemView.findViewById<TextView>(R.id.item_sub)

    }

    fun onClick(context: Context, position: Int) {
        val intent = Intent(context,CommunityPost::class.java)
        intent.putExtra("key",keyList[position])
        context.startActivity(intent)
    }

}
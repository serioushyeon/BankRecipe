package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solid.bankrecipe.R
import com.solid.bankrecipe.ui.community.CommunityData
import com.google.firebase.firestore.FirebaseFirestore

class RecipePostAdapter (val context: Context?, val itemList: ArrayList<RecipePostData>) :
    RecyclerView.Adapter<RecipePostAdapter.RecipePostViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("recipe")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                itemList.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(RecipePostData::class.java)
                    itemList.add(item!!)
                    keyList.add(snapshot.id)
                }

                notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_post_item, parent, false)
        return RecipePostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipePostViewHolder, position: Int) {
        holder.recipe_title.text = itemList[position].title
        holder.recipe_content.text = itemList[position].content
        val imView = itemList[position].imageUri?.get(0).toString()
        Glide.with(context!!).load(imView).into(holder.recipe_img)

        //Glide.with(context!!).load(itemList[position].img).into(holder.recipe_img)
        holder.itemView.setOnClickListener {
            Intent(this.context, RecipePostDetailActivity::class.java).apply {
                putExtra(
                    "item",
                    keyList[position]
                )
            }
                .run { context!!.startActivity(this) }
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipePostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_title = itemView.findViewById<TextView>(R.id.recipe_post_title)
        val recipe_content = itemView.findViewById<TextView>(R.id.recipe_post_content)
        val recipe_img = itemView.findViewById<ImageView>(R.id.recipe_post_img)

    }
}
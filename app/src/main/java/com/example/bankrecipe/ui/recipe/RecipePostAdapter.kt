package com.example.bankrecipe.ui.recipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bankrecipe.R

class RecipePostAdapter (val context: Context?, val itemList: ArrayList<RecipePostData>) :
    RecyclerView.Adapter<RecipePostAdapter.RecipePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_post_item, parent, false)
        return RecipePostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipePostViewHolder, position: Int) {
        holder.recipe_title.text = itemList[position].title
        holder.recipe_content.text = itemList[position].content
        Glide.with(context!!).load(itemList[position].img).into(holder.recipe_img)
        holder.itemView.setOnClickListener {
            /*Intent(this.context, RecipeDetailActivity::class.java).apply {
                putExtra(
                    "item",
                    itemList[position]
                )
            }
                .run { context.startActivity(this) }*/
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
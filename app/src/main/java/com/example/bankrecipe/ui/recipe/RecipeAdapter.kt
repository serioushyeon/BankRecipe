package com.example.bankrecipe.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R

class RecipeAdapter(val itemList : ArrayList<RecipeData>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
            return RecipeViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.recipe_title.text = itemList[position].title
            holder.recipe_content.text = itemList[position].content
            holder.recipe_time.text = itemList[position].time
            holder.recipe_level.text = itemList[position].level
            holder.recipe_kcal.text = itemList[position].kcal
        }

        override fun getItemCount(): Int {
            return itemList.count()
        }


        inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val recipe_title = itemView.findViewById<TextView>(R.id.recipe_list_item_title)
            val recipe_content = itemView.findViewById<TextView>(R.id.recipe_list_item_content)
            val recipe_time = itemView.findViewById<TextView>(R.id.recipe_list_item_time)
            val recipe_level = itemView.findViewById<TextView>(R.id.recipe_list_item_level)
            val recipe_kcal = itemView.findViewById<TextView>(R.id.recipe_list_item_kcal)

        }
}

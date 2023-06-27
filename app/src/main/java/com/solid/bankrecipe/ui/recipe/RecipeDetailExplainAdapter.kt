package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R

class RecipeDetailExplainAdapter (val context: Context?, val itemList : ArrayList<RecipeDetailExplainData>)  :
    RecyclerView.Adapter<RecipeDetailExplainAdapter.RecipeDetailExplainViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeDetailExplainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_detail_explain, parent, false)
        return RecipeDetailExplainViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDetailExplainViewHolder, position: Int) {
        holder.recipe_detail_num.text = itemList[position].exnum
        holder.recipe_detail_text.text = itemList[position].explain
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeDetailExplainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_detail_num = itemView.findViewById<TextView>(R.id.recipe_detail_explain_num)
        val recipe_detail_text = itemView.findViewById<TextView>(R.id.recipe_detail_explain_text)
    }
}
package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R
import kotlin.streams.toList

class RecipePostRegisterAdapter (val context: Context?, val itemList : ArrayList<String>)  :
    RecyclerView.Adapter<RecipePostRegisterAdapter.RecipePostRegisterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePostRegisterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_detail_ingredient, parent, false)
        return RecipePostRegisterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipePostRegisterViewHolder, position: Int) {
        holder.recipe_detail_ingredient.text = itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipePostRegisterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_detail_ingredient = itemView.findViewById<Button>(R.id.recipe_detail_btn)
    }
}

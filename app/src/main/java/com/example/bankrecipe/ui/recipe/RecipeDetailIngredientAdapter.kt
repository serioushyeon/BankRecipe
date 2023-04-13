package com.example.bankrecipe.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R

class RecipeDetailIngredientAdapter(val context: Context?, val itemList : ArrayList<RecipeDetaliIngredientData>)  :
    RecyclerView.Adapter<RecipeDetailIngredientAdapter.RecipeDetailIngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailIngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_detail_ingredient, parent, false)
        return RecipeDetailIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDetailIngredientViewHolder, position: Int) {
        holder.recipe_detail_ingredient.text = itemList[position].ingredient + " " + itemList[position].volume
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeDetailIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_detail_ingredient = itemView.findViewById<Button>(R.id.recipe_detail_btn)
    }
}

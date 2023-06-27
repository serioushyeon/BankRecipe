package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R

class RecipeIngredientPriceAdapter(val context: Context?, val itemList : ArrayList<RecipeIngredientPriceData>) :
    RecyclerView.Adapter<RecipeIngredientPriceAdapter.RecipeIngredientPriceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeIngredientPriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_ingredient_price_item, parent, false)
        return RecipeIngredientPriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeIngredientPriceViewHolder, position: Int) {
        holder.recipe_ing_mart.text = itemList[position].martName
        holder.recipe_ing_ing.text = itemList[position].ingredientName
        holder.recipe_ing_price.text = itemList[position].Price
        holder.recipe_ing_volume.text = itemList[position].volume
        holder.recipe_ing_date.text = itemList[position].date
        holder.recipe_ing_location.text = itemList[position].location
        if(itemList[position].volume == "")
            holder.layout.setColumnCollapsed(3, true)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeIngredientPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_ing_mart = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_mart)
        val recipe_ing_ing = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_ing)
        val recipe_ing_price = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_pr)
        val recipe_ing_volume = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_volume)
        val recipe_ing_date = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_date)
        val recipe_ing_location = itemView.findViewById<TextView>(R.id.recipe_ingredient_price_location)
        val layout = itemView.findViewById<TableLayout>(R.id.recipe_ing_price_layout)
    }
}
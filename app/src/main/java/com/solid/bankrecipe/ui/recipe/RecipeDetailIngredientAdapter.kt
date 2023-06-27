package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.streams.toList

class RecipeDetailIngredientAdapter(val context: Context?, val itemList : ArrayList<RecipeDetaliIngredientData>)  :
    RecyclerView.Adapter<RecipeDetailIngredientAdapter.RecipeDetailIngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailIngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_detail_ingredient, parent, false)
        return RecipeDetailIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDetailIngredientViewHolder, position: Int) {
        var getItem = RecipeIngredientPrice.getPriceItem().stream().filter { item -> item.ingredientName.contains(itemList[position].ingredient) }.toList() as ArrayList<RecipeIngredientPriceData>

        holder.recipe_detail_ingredient.text = itemList[position].ingredient + " " + itemList[position].volume
        holder.recipe_detail_ingredient.setOnClickListener {
            if(getItem.isNotEmpty()) {
                Intent(context, RecipeIngredientPriceActivity::class.java).apply { putExtra("item", getItem) }
                    .run { context?.startActivity(this) }
            }
            else{
                Toast.makeText(context, "물가 정보가 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeDetailIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_detail_ingredient = itemView.findViewById<Button>(R.id.recipe_detail_btn)
    }
}

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
import kotlin.streams.toList

class RecipeForIngredientAdapter(
    val context: Context?,
    val correctRecipes: ArrayList<RecipeData>,
    val itemList: HashMap<String, Int>
) :
    RecyclerView.Adapter<RecipeForIngredientAdapter.RecipeForIngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeForIngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_for_ing_item, parent, false)
        return RecipeForIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeForIngredientViewHolder, position: Int) {
            var ingitem = LoadRecipeData.getIngItem().stream()
                .filter { item -> item.num == correctRecipes[position].num }
                .toList() as ArrayList<RecipeDetaliIngredientData>
            holder.recipe_title.text = correctRecipes[position].title
            holder.recipe_summary.text = correctRecipes[position].summary
            holder.recipe_num.text =
                ingitem.size.toString() + "개 중 " + itemList[correctRecipes[position].num].toString() + "개 포함"
            Glide.with(context!!).load(correctRecipes[position].imgUrl).into(holder.recipe_img)
            holder.itemView.setOnClickListener {
                Intent(this.context, RecipeDetailActivity::class.java).apply {
                    putExtra(
                        "item",
                        correctRecipes[position]
                    )
                }
                    .run { context.startActivity(this) }
            }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeForIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_title = itemView.findViewById<TextView>(R.id.rec_for_ing_title)
        val recipe_summary = itemView.findViewById<TextView>(R.id.rec_for_ing_con)
        val recipe_num = itemView.findViewById<TextView>(R.id.rec_for_ing_num)
        val recipe_img = itemView.findViewById<ImageView>(R.id.rec_for_ing_img)

    }
}
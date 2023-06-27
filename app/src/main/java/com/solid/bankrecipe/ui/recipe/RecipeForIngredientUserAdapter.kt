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
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.streams.toList

class RecipeForIngredientUserAdapter(
    val context: Context?,
    val sItem: ArrayList<RegisterIngredientData>,
    var itemList : ArrayList<RecipePostData>
) :
    RecyclerView.Adapter<RecipeForIngredientUserAdapter.RecipeForIngredientUserAdapterViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    var correctPost : MutableMap<String, Int> = HashMap()

    private var keyList = HashMap<RecipePostData,String>()
    init {
        var cp = ArrayList<RecipePostData>()
        var correctPosts : MutableMap<RecipePostData, Int> = HashMap()
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("recipe")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                itemList.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(RecipePostData::class.java)
                    itemList.add(item!!)
                    keyList[item] = snapshot.id
                }
                for(post in itemList) {
                    for (pIng in post.ingredient) {
                        for (sitem in sItem) {
                            if (sitem.ingredient == pIng) {
                                if (correctPost.containsKey(post.uid))
                                    correctPost.put(post.uid, correctPost.get(post.uid)!! + 1)
                                else
                                    correctPost[post.uid] = 1
                            }
                        }
                    }
                }
                cp = itemList.stream().filter { item -> correctPost.containsKey(item.uid) }.toList() as ArrayList<RecipePostData>
                for(item in cp){
                    correctPosts[item] = correctPost[item.uid]!!
                }
                if(correctPosts.isNotEmpty()) {
                    correctPosts = correctPosts.toList().sortedByDescending { it.second }
                        .toMap() as MutableMap<RecipePostData, Int>
                    cp = ArrayList<RecipePostData>(correctPosts.keys.toList())
                }
                else{
                    cp = ArrayList()
                }
                itemList = cp
                var temp : HashMap<RecipePostData, String> = HashMap()
                for(i in itemList)
                {
                    if(keyList.containsKey(i))
                        temp[i] = keyList[i]!!
                }
                keyList = temp
                notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeForIngredientUserAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_for_ing_item, parent, false)
        return RecipeForIngredientUserAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeForIngredientUserAdapterViewHolder, position: Int) {

            holder.recipe_title.text = itemList[position].title
            holder.recipe_summary.text = itemList[position].content
            holder.recipe_num.text =
                itemList[position].ingredient.size.toString() + "개 중 " + correctPost[itemList[position].uid].toString() + "개 포함"
            Glide.with(context!!).load(itemList[position].imageUri!![0])
                .into(holder.recipe_img)
            holder.itemView.setOnClickListener {
                Intent(this.context, RecipePostDetailActivity::class.java).apply {
                    putExtra(
                        "item",
                        keyList[itemList[position]]
                    )
                }
                    .run { context.startActivity(this) }
            }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class RecipeForIngredientUserAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipe_title = itemView.findViewById<TextView>(R.id.rec_for_ing_title)
        val recipe_summary = itemView.findViewById<TextView>(R.id.rec_for_ing_con)
        val recipe_num = itemView.findViewById<TextView>(R.id.rec_for_ing_num)
        val recipe_img = itemView.findViewById<ImageView>(R.id.rec_for_ing_img)

    }
}
package com.solid.bankrecipe.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.solid.bankrecipe.R

class RegisterIngredientAdapter(val context: Context?, var itemList : ArrayList<RegisterIngredientData>) :
    RecyclerView.Adapter<RegisterIngredientAdapter.RegisterIngredientAdapterViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int, item : CheckedTextView)
    }
    //객체 저장 변수
    private lateinit var mOnItemClickListener: OnItemClickListener
    //객체 전달 메서드
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        mOnItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegisterIngredientAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.register_ingredient_item, parent, false)
        return RegisterIngredientAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegisterIngredientAdapterViewHolder, position: Int) {
        holder.register_ing_text.text = itemList[position].ingredient
        holder.register_ing_text.isChecked = itemList[position].checked
        //holder.register_ing_text.setOnClickListener{
        //    itemList[position].checked = !itemList[position].checked
        //    holder.register_ing_text.isChecked = itemList[position].checked
        //    notifyDataSetChanged()
        //}
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }
    fun setItems(items : ArrayList<RegisterIngredientData>){
        itemList = items
        notifyDataSetChanged()

    }


    inner class RegisterIngredientAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val register_ing_text= itemView.findViewById<CheckedTextView>(R.id.register_ing_item_check)
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(itemView, pos, register_ing_text)
                }
            }
        }
        val register_ing_text= itemView.findViewById<CheckedTextView>(R.id.register_ing_item_check)
    }
}
package com.example.bankrecipe.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.bankrecipe.databinding.CardAdapterBinding
import com.example.bankrecipe.ui.home.CardAdapter.Companion.MAX_ELEVATION_FACTOR

class CardPagerAdapter(val context: Context): CardAdapter, PagerAdapter(){
    private var mViews: MutableList<CardView> = mutableListOf()
    private var mData: MutableList<CardItem> = mutableListOf()
    private lateinit var binding : CardAdapterBinding
    private var mBaseElevation = 0f

    override fun getBaseElevation(): Float {
        return mBaseElevation
    }

    override fun getCardViewAt(position: Int): CardView {
        return mViews[position]
    }

    fun addCardItem(item: CardItem) {
        mData.add(item)
    }
    fun getCardItem(index: Int): CardItem {
        return mData[index]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater

        binding = CardAdapterBinding.inflate(inflater)
        binding.contentText.text = mData[position].getText()
        Glide.with(context!!).load(mData[position].getImg()).into(binding.cardAdapterImg)
        binding.cardView.maxCardElevation = mBaseElevation * MAX_ELEVATION_FACTOR

        if (mBaseElevation == 0f) {
            mBaseElevation = binding.cardView.cardElevation
        }

        binding.cardView.maxCardElevation = mBaseElevation * MAX_ELEVATION_FACTOR

        mViews.add(binding.cardView)
        container.addView(binding.root)

        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mData.size
    }

    fun getRegisteredView(position: Int): CardView? {
        return mViews[position]
    }
}
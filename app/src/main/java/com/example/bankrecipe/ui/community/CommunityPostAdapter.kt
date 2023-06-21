package com.example.bankrecipe.ui.community

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bankrecipe.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class CommunityPostAdapter(var itemList: ArrayList<CommunityData>, val key:String, private var context: Context) :

    RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    private lateinit var imView: String
    private var img =  arrayListOf<String>()
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("photo")?.addSnapshotListener{
                querySnapshot,firebaseFirestoreException ->
            itemList.clear()
            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(CommunityData::class.java)
                itemList.add(item!!)
                keyList.add(snapshot.id)
            }

            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_image_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommunityPostAdapter.ViewHolder, position: Int) {


                    //imageArea.isVisible = true
                    Log.d("key 값 ", key)
                    Log.d("key 값 ", itemList.size.toString())
                    Log.d("keyposition",keyList[position])
        firestore.collection("photo").document(key).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var photo = task.result?.toObject(CommunityData::class.java)
                img = photo?.imageUri!!
                holder.bindItems((photo?.imageUri?.get(position) ?: String) as String)
            }
        }

    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: String) {
            val imageArea = itemView.findViewById<ImageView>(R.id.image)
            Glide.with(context).load(item).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageArea)
            Log.d("이미지 uri ", item)
        }

    }



}
package com.example.bankrecipe.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm")
    var mFirebaseAuth = FirebaseAuth.getInstance()
    var mFirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chatViewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var recyclerView = binding.chatfragmentRecyclerview
        recyclerView.adapter = ChatRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        return root
    }

    inner class ChatRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var chatModels = ArrayList<ChatModel>()
        var uid : String
        var destinationUsers = ArrayList<String>()
        var keys = ArrayList<String>()
        init {
            if(mFirebaseAuth.currentUser == null){
                uid = "null"
                Toast.makeText(context, "로그인 해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                uid = mFirebaseAuth.currentUser!!.uid

                mFirebaseDatabase.reference.child("chatrooms").orderByChild("users/" + uid)
                    .equalTo(true).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatModels.clear()
                        for (item: DataSnapshot in snapshot.children) {
                            chatModels.add(item.getValue(ChatModel::class.java)!!)
                            print(chatModels)
                            keys.add(item.key!!)
                        }
                        notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
       }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(uid != "null"){
            var customViewHolder = holder as CustomViewHolder
            var destinationUid : String = "null"

            // 일일 챗방에 있는 유저를 체크
            for (user in chatModels.get(position).users!!.keys) {
                if (user != uid) {
                    destinationUid = user
                    destinationUsers.add(destinationUid)
                }
            }
            FirebaseFirestore.getInstance().collection("user").document(destinationUid).get().addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    val userModel = task.result?.toObject(UserModel::class.java)!!
                    customViewHolder.textView_title.text = userModel!!.userName
                }
            }

            //메시지를 내림 차순으로 정렬 후 마지막 메세지의 키값을 가져옴
            val commentMap: TreeMap<String, ChatModel.Companion.Comment> = TreeMap(Collections.reverseOrder())
            commentMap.putAll(chatModels.get(position).comments!!)
            if(commentMap.keys.size>0) {
            val lastMessageKey = commentMap.keys.toTypedArray()[0]

                customViewHolder.textView_last_message.text = chatModels[position].comments!![lastMessageKey]!!.message
                //TimeStamp
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                val unixTime = chatModels!!.get(position).comments!!.get(lastMessageKey)!!.timestamp.toString().toLong()
                val date = Date(unixTime)
                var time = simpleDateFormat.format(date)
                customViewHolder.textView_timestamp.text = time
            }

            customViewHolder.itemView.setOnClickListener { view ->
                val intent = Intent(view.context, ChatingActivity ::class.java)
                intent.putExtra("destinationUid", destinationUsers[position])
                view.context.startActivity(intent)
            }

            holder.itemView.setOnClickListener {
                var intent : Intent? = null
                intent = Intent(holder.itemView.context, ChatingActivity::class.java)
                intent.putExtra("destinationUid", destinationUsers.get(position))
                startActivity(intent)
            }
            }

        }

        override fun getItemCount(): Int {
            return chatModels.size
        }
        inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            var textView_title = itemView.findViewById<TextView>(R.id.chatitem_textview_title);
            var textView_last_message = itemView.findViewById<TextView>(R.id.chatitem_textview_lastMessage);
            var textView_timestamp = itemView.findViewById<TextView>(R.id.chatitem_textview_timestamp);
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
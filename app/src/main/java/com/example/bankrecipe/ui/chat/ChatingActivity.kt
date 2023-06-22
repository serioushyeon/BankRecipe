package com.example.bankrecipe.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityChatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class ChatingActivity : AppCompatActivity() {
    lateinit var destinetionUid : String
    lateinit var button : Button
    lateinit var editText : EditText
    lateinit var binding : ActivityChatingBinding
    lateinit var uid : String
    lateinit var chatRoomUid : String
    lateinit var rv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uid = FirebaseAuth.getInstance().currentUser!!.uid // 단말기ㅣ에 로그인된 UID
        destinetionUid = intent.getStringExtra("destinationUid")!!//채팅을 당하는 아이디
        button = binding.chatingActivityButton
        editText = binding.chatingActivityEditText
        rv = binding.chatingActivityRecyclerView

        button.setOnClickListener {
            var chatModel = ChatModel()
            chatModel.users.put(uid, true)
            chatModel.users.put(destinetionUid,true)

            if(chatRoomUid == null) {
                button.isEnabled = false
                FirebaseDatabase.getInstance().reference.child("chatrooms").push().setValue(chatModel).addOnSuccessListener { checkChatRoom()}
            }else {
                var comment = ChatModel().Comment()
                comment.uid = uid
                comment.message = editText.text.toString()
                FirebaseDatabase.getInstance().reference.child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment)
            }
        }
        checkChatRoom()
    }

    fun checkChatRoom(){

        FirebaseDatabase.getInstance().reference.child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var chatModel = snapshot.value as ChatModel
                if (chatModel.users.containsKey(destinetionUid)){
                    chatRoomUid = snapshot.key!!
                    button.isEnabled  = true
                    rv.layoutManager = LinearLayoutManager(this@ChatingActivity, LinearLayoutManager.HORIZONTAL, false)
                    rv.adapter = RecyclerViewAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var comments = ArrayList<ChatModel.Comment?>()
        lateinit var userModel : UserModel
        init {
            FirebaseFirestore.getInstance().collection("user").document(destinetionUid).get().addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    userModel = task.result?.toObject(UserModel::class.java)!!
                    getMessageList()
                }
            }
        }
        fun getMessageList() {
            FirebaseDatabase.getInstance().reference.child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    comments.clear()

                    for(item : DataSnapshot in snapshot.children){
                        comments.add(item.getValue(ChatModel.Comment::class.java))
                    }
                    notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        override fun getItemCount(): Int {
            return comments.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
            var rightview = LayoutInflater.from(parent.context).inflate(R.layout.chat_right_item, parent, false)
            return ChatingViewHolder(view, rightview)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val h = holder as ChatingViewHolder
            if(comments.get(position)!!.uid.equals(uid)){
                //h.textView_name.setText(userModel.userName)
                //h.constraintLayout_destination.visibility = View.VISIBLE
                //h.textView_message.setBackgroundResource(R.drawable.ic_chat_right_item)
                //h.textView_message.setText(comments.get(position)!!.message)
                h.right_message.setText(comments.get(position)!!.message)
            }
            else{
                h.textView_message.setText(comments.get(position)!!.message)
                h.textView_message.setBackgroundResource(R.drawable.ic_chat_left_item)
                h.constraintLayout_destination.visibility = View.INVISIBLE
            }

        }
        inner class ChatingViewHolder(itemView: View, rightView : View) : RecyclerView.ViewHolder(itemView){
            var textView_message : TextView
            var textView_name : TextView
            var constraintLayout_destination : ConstraintLayout
            var right_message : TextView

            init {
                textView_message = itemView.findViewById(R.id.chatingActivity_recyclerView)
                textView_name = itemView.findViewById(R.id.messageItem_textView_name)
                constraintLayout_destination = itemView.findViewById(R.id.messageItem_constraintLayout)
                right_message = rightView.findViewById(R.id.msg_text)
            }
        }

    }
}
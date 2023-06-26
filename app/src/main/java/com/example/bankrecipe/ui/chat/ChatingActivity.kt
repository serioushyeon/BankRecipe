package com.example.bankrecipe.ui.chat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityChatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatingActivity : AppCompatActivity() {
    var mFirebaseDatabase = FirebaseDatabase.getInstance()
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var destinationUid : String
    lateinit var button : Button
    lateinit var editText : EditText
    lateinit var binding : ActivityChatingBinding
    lateinit var uid : String
    lateinit var userModel : UserModel
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
    var chatroomUid: String? = null
    lateinit var rv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uid = mFirebaseAuth.currentUser!!.uid // 단말기ㅣ에 로그인된 UID
        destinationUid = intent.getStringExtra("destinationUid")!!//채팅을 당하는 아이디
        button = binding.chatingActivityButton
        editText = binding.chatingActivityEditText
        rv = binding.chatingActivityRecyclerView
        button.setOnClickListener {
            var chatModel = ChatModel()
            chatModel.users?.put(uid, true)
            chatModel.users?.put(destinationUid, true)
            var comments = ChatModel.Companion.Comment()
            comments.uid = uid
            comments.message = editText.text.toString()
                if(chatroomUid == null) {
                button.isEnabled = false
                    mFirebaseDatabase.reference.child("chatrooms").push().setValue(chatModel).addOnSuccessListener { checkChatRoom()}
            }else {
                comments.timestamp = ServerValue.TIMESTAMP
                    mFirebaseDatabase.reference.child("chatrooms").child(chatroomUid!!).child("comments").push().setValue(comments).addOnSuccessListener {
                    editText.setText("")
                }
            }
        }
        checkChatRoom()
    }

    fun checkChatRoom(){

        mFirebaseDatabase.reference.child("chatrooms").orderByChild("users/${uid}").equalTo(true).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(item in snapshot.children) {
                    var chatModel = item.getValue(ChatModel::class.java)!!
                    if (chatModel.users!!.containsKey(destinationUid)) {
                        chatroomUid = item.key!!
                        Log.d("chatkey", chatroomUid!!)
                        button.isEnabled = true
                        rv.layoutManager = LinearLayoutManager(
                            this@ChatingActivity
                        )
                        rv.adapter = RecyclerViewAdapter()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        lateinit var userModel : UserModel
        var comments : ArrayList<ChatModel.Companion.Comment>
        init {
            comments = ArrayList()
            FirebaseFirestore.getInstance().collection("user").document(destinationUid).get().addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    userModel = task.result?.toObject(UserModel::class.java)!!
                    getMessageList()
                }
            }
        }
        fun getMessageList() {
            FirebaseDatabase.getInstance().reference.child("chatrooms").child(chatroomUid!!).child("comments").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    comments.clear()
                    var readUsersMap = HashMap<String, Any>()
                    for(item : DataSnapshot in snapshot.children){
                        var key = item.key
                        var commentOrigin = item.getValue(ChatModel.Companion.Comment::class.java)
                        var commentModify = item.getValue(ChatModel.Companion.Comment::class.java)
                        commentModify!!.readUsers.put(uid, true)
                        readUsersMap.put(key!!, commentModify)
                        comments.add(commentOrigin!!)
                    }
                    notifyDataSetChanged()
                    rv.scrollToPosition(comments.size - 1)
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
            return ChatingViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val h = holder as ChatingViewHolder
            val unixTime = comments[position]!!.timestamp as Long
            val date = Date(unixTime)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            val time = simpleDateFormat.format(date)
            //내가 보낸 메시지, 오른쪽에 뜨기
           if(comments.get(position)!!.uid.equals(uid)){
               h.constraintLayout_left.visibility = View.GONE
               h.constraintLayout_right.visibility = View.VISIBLE
               h.textView_message_right.setText(comments[position]!!.message)
               h.textView_message_right.setTextColor(Color.WHITE)
               h.timeStamp_right.setText(time)

            }
            //상대방, 왼쪽에 뜨기
            else{
                h.constraintLayout_right.visibility = View.GONE
                h.constraintLayout_left.visibility = View.VISIBLE
                h.left_name.setText(userModel.userName)
                h.left_message.setText(comments[position]!!.message)
                h.left_timeStamp.setText(time)

            }
        }
        inner class ChatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var textView_message_right = itemView.findViewById<TextView>(R.id.messageItem_textView_right_text)
            var timeStamp_right = itemView.findViewById<TextView>(R.id.messageItem_textView_right_time)
            var constraintLayout_right = itemView.findViewById<ConstraintLayout>(R.id.messageItem_right_constraintLayout)
            var left_message = itemView.findViewById<TextView>(R.id.massageItem_textView_message)
            var left_name = itemView.findViewById<TextView>(R.id.messageItem_textView_name)
            var left_timeStamp = itemView.findViewById<TextView>(R.id.massageItem_textView_send_time_text)
            var constraintLayout_left = itemView.findViewById<ConstraintLayout>(R.id.messageItem_left_constraintLayout)

        }

    }

    override fun onBackPressed() {
        finish();
    }
}
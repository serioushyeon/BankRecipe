package com.example.bankrecipe.ui.chat

data class ChatModel (var users: HashMap<String, Boolean> = HashMap(), var comments : HashMap<String, Boolean> = HashMap())
{
    inner class Comment{
        lateinit var uid : String
        lateinit var message : String
    }
}
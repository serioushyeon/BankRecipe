package com.solid.bankrecipe.ui.chat

data class ChatModel(var users: HashMap<String, Boolean>? = HashMap(), var comments: HashMap<String, Comment>? = HashMap()) {
    companion object {
        data class Comment(var uid: String? = null, var message: String? = null, var timestamp: Any? = null, var readUsers: HashMap<String, Any> = HashMap())
    }

}
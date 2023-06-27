package com.solid.bankrecipe.ui.chat

data class NotificationModel(var to: String? = null, var notification: Notification? = Notification(),var data:Data?=Data()) {
    companion object {
        data class Notification(var title: String? = null, var text: String? = null)
        data class Data(var title:String? =null,var text:String?=null)
    }

}
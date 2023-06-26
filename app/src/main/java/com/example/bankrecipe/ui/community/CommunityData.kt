package com.example.bankrecipe.ui.community

data class CommunityData(
    var title: String="",
    var price: String="",
    var make: String="",
    var period: String="",
    var subtext: String="",
    val imageUri: ArrayList<String>?=ArrayList<String>(),
    val date: String="",
    var id:String?=null,
    val uid: String = "",
    val ekey: String = "",
    var map:String?=null
        )
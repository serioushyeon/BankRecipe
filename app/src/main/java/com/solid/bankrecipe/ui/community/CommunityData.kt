package com.solid.bankrecipe.ui.community

data class CommunityData(
    var title: String="",
    var price: String="",
    var make: String="",
    var period: String="",
    var subtext: String="",
    var imageUri: ArrayList<String>?=null,
    val date: String="",
    var id:String?=null,
    val uid: String = "",
    val ekey: String = "",
    var map:String?=null
        )
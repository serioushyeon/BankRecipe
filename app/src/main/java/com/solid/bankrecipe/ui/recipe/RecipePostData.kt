package com.solid.bankrecipe.ui.recipe

data class RecipePostData(
    val title : String = "",
    val ingredient : ArrayList<String> = ArrayList<String>(),
    val content : String = "",
    var imageUri: ArrayList<String>?=null,
    val uid : String = "",
    val price: String = "",
    val date: String = "",
    val kcal : String ="",
    //val tan : String = "",
    //val dan : String = "",
    //val ji : String = ""
) : java.io.Serializable

package com.example.bankrecipe.ui.recipe

data class RecipePostData(
    val title : String = "",
    val ingredient : ArrayList<String> = ArrayList<String>(),
    val content : String = "",
    val img : String = "",
    val uid : String = "",
    val price: String = "",
    val date: String = "",
    val kcal : String ="",
    //val tan : String = "",
    //val dan : String = "",
    //val ji : String = ""
)

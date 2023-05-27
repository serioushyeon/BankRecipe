package com.example.bankrecipe.ui.recipe

data class RecipePostData(
    val title : String,
    val ingredient : ArrayList<String>,
    val content : String,
    val img : String,
    val uid : String,
    val price: String
)

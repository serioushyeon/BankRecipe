package com.example.bankrecipe.ui.recipe

import java.io.Serializable

data class RecipeData (
    val num : String,
    val title : String,
    val summary : String,
    val time : String,
    val level: String,
    val kcal : String,
    val category: String,
    val imgUrl : String,
    val price : String,
    //val img : 타입
) : Serializable
//인텐트로 넘기기 위해 serializable
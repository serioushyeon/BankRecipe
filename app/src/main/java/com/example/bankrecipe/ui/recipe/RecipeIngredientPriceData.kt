package com.example.bankrecipe.ui.recipe

import java.io.Serializable

data class RecipeIngredientPriceData(
    val martName : String,
    val ingredientName : String,
    val volume : String,
    val Price : String,
    val date : String,
    val location : String
) : Serializable

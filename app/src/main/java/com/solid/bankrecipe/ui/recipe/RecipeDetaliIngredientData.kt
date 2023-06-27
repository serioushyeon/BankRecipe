package com.solid.bankrecipe.ui.recipe

import java.io.Serializable

data class RecipeDetaliIngredientData(
    val num : String,
    val ingredient : String,
    val volume : String,
    val category : String
) : Serializable

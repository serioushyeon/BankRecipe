package com.solid.bankrecipe.ui.recipe

import org.apache.commons.text.matcher.StringMatcher

data class RegisterIngredientData(
    var ingredient : String,
    var checked : Boolean,
    var kcal : String
) : java.io.Serializable

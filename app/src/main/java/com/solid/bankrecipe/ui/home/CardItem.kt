package com.solid.bankrecipe.ui.home

import com.solid.bankrecipe.ui.recipe.RecipeData

class CardItem {
    private var mTextResource: String
    private var mTitleResource : String
    private var mImgResource : String
    private var mRecipeResource : RecipeData

    constructor(title: String, text: String, imgUrl: String, recipe: RecipeData) {
        mTitleResource = title
        mTextResource = text
        mImgResource = imgUrl
        mRecipeResource = recipe

    }

    fun getText(): String {
        return mTextResource
    }

    fun getTitle(): String {
        return mTitleResource
    }

    fun getImg():String {
        return mImgResource
    }

    fun getRecipe() : RecipeData{
        return mRecipeResource
    }
}
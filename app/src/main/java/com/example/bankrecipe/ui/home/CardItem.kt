package com.example.bankrecipe.ui.home

class CardItem {
    private var mTextResource: String
    private var mTitleResource : String
    private var mImgResource : String

    constructor(title: String, text: String, imgUrl: String) {
        mTitleResource = title
        mTextResource = text
        mImgResource = imgUrl
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
}
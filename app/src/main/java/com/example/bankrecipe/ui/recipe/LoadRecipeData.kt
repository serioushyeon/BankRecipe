package com.example.bankrecipe.ui.recipe

import android.content.res.AssetManager
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

object LoadRecipeData {
    var getExItem = ArrayList<RecipeDetailExplainData>()
    var getIngItem = ArrayList<RecipeDetaliIngredientData>()
    var getGeneralItem = ArrayList<RecipeData>()

    fun updateExItem(assets: AssetManager) {
        val assetManager = assets
        val inputStream: InputStream = assetManager.open("recipe_explain.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream))//UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            getExItem.add(RecipeDetailExplainData(content[0], content[1], content[2]))
        }
    }

    fun updateIngItem(assets: AssetManager) {
        val assetManager = assets
        val inputStream: InputStream = assetManager.open("recipe_ingredient.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream)) //UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            getIngItem.add(RecipeDetaliIngredientData(content[0], content[2], content[3], content[6]))
        }
    }
    fun updateGeneralItem(assets: AssetManager){
        val assetManager = assets
        val inputStream: InputStream = assetManager.open("recipe_general.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "UTF-8"))
        val allContent = csvReader.readAll() as List<Array<String>>
        for (content in allContent) {
            getGeneralItem.add(RecipeData(content[0], content[1], content[2], content[7], content[10], content[8], content[4], content[13], content[12]))
        }
    }

    fun getExItem() : ArrayList<RecipeDetailExplainData> {
        return getExItem
    }
    fun getIngItem() : ArrayList<RecipeDetaliIngredientData>{
        return getIngItem
    }
    fun getGeneralItem() : ArrayList<RecipeData>{
        return getGeneralItem
    }
}
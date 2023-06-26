package com.example.bankrecipe.ui.recipe

import android.content.res.AssetManager
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

object LoadKcalData {
    var getKcalItem = ArrayList<RecipeKcalData>()

    fun updateKcalItem(assets: AssetManager) {
        val assetManager = assets
        val inputStream: InputStream = assetManager.open("KcalData.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream))//UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            getKcalItem.add(RecipeKcalData(content[2], content[0], content[7]))
        }
    }


    fun getKcalItem() : ArrayList<RecipeKcalData> {
        return getKcalItem
    }

}
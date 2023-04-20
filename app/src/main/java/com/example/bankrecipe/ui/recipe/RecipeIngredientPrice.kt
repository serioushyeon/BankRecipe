package com.example.bankrecipe.ui.recipe

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

object RecipeIngredientPrice{
    var getItem = ArrayList<RecipeIngredientPriceData>()

    fun updatePriceItem(){
        //api 사용시 필요 코드
        val thread = Thread(NetworkThread(
            "http://openAPI.seoul.go.kr:8088/7a5a7674487a7a343537484f434a47/xml/ListNecessariesPricesService/1/999",
            getItem
        )
        )
        thread.start()
        thread.join()
    }

    fun getPriceItem() : ArrayList<RecipeIngredientPriceData> {
        return getItem
    }

    class NetworkThread(var url: String, var itemLists : ArrayList<RecipeIngredientPriceData>): Runnable {

        override fun run() {

            try {

                val xml: Document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)


                xml.documentElement.normalize()

                //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
                val list: NodeList = xml.getElementsByTagName("row")

                //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다
                for (i in 0..list.length - 1) {

                    val n: Node = list.item(i)

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        val elem = n as Element

                        val map = mutableMapOf<String, String>()


                        // 이부분은 어디에 쓰이는지 잘 모르겠다.
                        for (j in 0..elem.attributes.length - 1) {

                            map.putIfAbsent(
                                elem.attributes.item(j).nodeName,
                                elem.attributes.item(j).nodeValue
                            )

                        }
                        itemLists.add(
                            RecipeIngredientPriceData(
                                elem.getElementsByTagName("M_NAME").item(0).textContent,
                                elem.getElementsByTagName("A_NAME").item(0).textContent,
                                elem.getElementsByTagName("A_UNIT").item(0).textContent,
                                elem.getElementsByTagName("A_PRICE").item(0).textContent,
                                elem.getElementsByTagName("P_DATE").item(0).textContent,
                                elem.getElementsByTagName("M_GU_NAME").item(0).textContent
                            ))
                    }
                }
            } catch (e: Exception) {
                Log.d("TTT", "오픈API" + e.toString())
            }
        }
    }
}
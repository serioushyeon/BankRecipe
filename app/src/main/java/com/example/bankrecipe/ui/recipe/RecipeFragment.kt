package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.bankrecipe.databinding.FragmentRecipeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var getItemList = loadData()

        //api 사용시 필요 코드
        /*val thread = Thread(NetworkThread(
                "http://211.237.50.150:7080/openapi/785893e32e057603199971d1697e86eef08d14a127fd4ac8c4df404973d24fba/xml/Grid_20150827000000000226_1/1/597",
                getitemlist
            )
        )
        thread.start()
        thread.join()*/

        initViewPager(getItemList)

        return root
    }
    //api 사용 시 필요 코드
    /*class NetworkThread(var url: String, var itemList : ArrayList<RecipeData>): Runnable {

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
                        //itemList.add(RecipeData(elem.getElementsByTagName("RECIPE_NM_KO").item(0).textContent,elem.getElementsByTagName("SUMRY").item(0).textContent,elem.getElementsByTagName("COOKING_TIME").item(0).textContent, elem.getElementsByTagName("LEVEL_NM").item(0).textContent, elem.getElementsByTagName("CALORIE").item(0).textContent, elem.getElementsByTagName("NATION_NM").item(0).textContent, "http://file.okdab.com/UserFiles/searching/recipe/000200.jpg"))
                    }
                }
            } catch (e: Exception) {
                Log.d("TTT", "오픈API" + e.toString())
            }
        }
    }*/
    private fun initViewPager(itemList: ArrayList<RecipeData>) {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = RecipeViewPager2Adapter(this.requireActivity())
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "한식"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "서양"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "중국"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "일본"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "동남아시아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "이탈리아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "퓨전"))


        //Adapter 연결
        binding.recipeViewpager.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.recipeTab, binding.recipeViewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "한식"
                1 -> tab.text = "양식"
                2 -> tab.text = "중식"
                3 -> tab.text = "일식"
                4 -> tab.text = "동남아시아"
                5 -> tab.text = "이탈리아"
                6 -> tab.text = "퓨전"
            }
        }.attach()
    }
    private fun loadData(): ArrayList<RecipeData> {
        var itemList = ArrayList<RecipeData>()
        val assetManager = this.requireActivity().assets
        val inputStream: InputStream = assetManager.open("recipe_general.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "UTF-8"))
        val allContent = csvReader.readAll() as List<Array<String>>
        for (content in allContent) {
            itemList.add(RecipeData(content[0], content[1], content[2], content[7], content[10], content[8], content[4], content[13], content[12]))
        }
        return itemList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
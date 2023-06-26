package com.example.bankrecipe.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.bankrecipe.databinding.ActivityRecipeForIngredientBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import java.security.cert.CRL
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.streams.toList

class RecipeForIngredientActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeForIngredientBinding
    lateinit var selectIngItem : ArrayList<RegisterIngredientData>
    lateinit var adapter: RecipeForIngredientAdapter
    var correctRecipe : MutableMap<String, Int> = HashMap()
    var correctRecipes : MutableMap<RecipeData, Int> = HashMap()
    lateinit var firestore: FirebaseFirestore
    private val keyList = arrayListOf<String>()
    lateinit var cr : ArrayList<RecipeData>
    lateinit var cr2 : ArrayList<RecipeData>
    var itemList = ArrayList<RecipePostData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeForIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var getItemList = LoadRecipeData.getGeneralItem()
        initViewPager(getItemList)
        selectIngItem = intent.getSerializableExtra("item") as ArrayList<RegisterIngredientData>

        val ingitem =LoadRecipeData.getIngItem()

        for(item in ingitem){
            for(sitem in selectIngItem){
                if(sitem.ingredient == item.ingredient) {
                    if (correctRecipe.containsKey(item.num))
                        correctRecipe.put(item.num, correctRecipe.get(item.num)!! + 1)
                    else
                        correctRecipe[item.num] = 1
                }
            }
        }

        //correctRecipe = correctRecipe.toList().sortedByDescending { it.second }.toMap() as MutableMap<String, Int>
        cr = LoadRecipeData.getGeneralItem.stream().filter { item -> correctRecipe.containsKey(item.num) }.toList() as ArrayList<RecipeData>
        for(item in cr){
            correctRecipes[item] = correctRecipe[item.num]!!
        }

        correctRecipes = correctRecipes.toList().sortedByDescending { it.second }.toMap() as MutableMap<RecipeData, Int>
        cr = correctRecipes.keys.toList() as ArrayList<RecipeData>


        //유저 레시피
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("recipe")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                itemList.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(RecipePostData::class.java)
                    itemList.add(item!!)
                    keyList.add(snapshot.id)
                }
            }
        //post의 재료 읽어오고 string비교 일치하면
        //그 post의 uid, 카운트 해시맵 저장, 증가
        //일치하는 순으로 정렬

    }
    private fun initViewPager(itemList: ArrayList<RecipeData>) {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adapter = RecipeViewPager2Adapter(this)
        /*viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "한식"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "서양"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "중국"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "일본"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "동남아시아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "이탈리아"))
        viewPager2Adapter.addFragment(RecipeTabFragment(itemList, "퓨전"))*/
        viewPager2Adapter.addFragment(RecipeForIngredientBasicFragment(cr, correctRecipe))
        viewPager2Adapter.addFragment(RecipePostFragment()) //수정, 프래그먼트 만들기


        //Adapter 연결
        binding.recipeForIngredientVP.apply {
            adapter = viewPager2Adapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.recipeForIngredientTab, binding.recipeForIngredientVP) { tab, position ->
            when (position) {
                0 -> tab.text = "기본 레시피"
                1 -> tab.text = "우리들의 레시피"
            }
        }.attach()
    }
}

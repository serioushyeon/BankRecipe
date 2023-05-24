package com.example.bankrecipe.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.bankrecipe.databinding.FragmentHomeBinding
import com.example.bankrecipe.ui.recipe.BudgetActivity
import com.example.bankrecipe.ui.recipe.RecipeData
import com.example.bankrecipe.ui.recipe.RegisterIngredientActivity
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.streams.toList


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var getItemList = loadData() //item

        //랜덤 정수 가져오기
        val set: MutableSet<Int> = HashSet()
        while (set.size < 4) {
            val d = Math.random() * getItemList.size
            set.add(d.toInt())
        }
        val list: List<Int> = ArrayList(set)
        Collections.sort(list)

        //메뉴 추천 카드
        val cardAdapter =
            CardPagerAdapter(requireActivity().applicationContext)
        cardAdapter.addCardItem(
            CardItem(
                getItemList[list[0]].title,
                getItemList[list[0]].title,
                getItemList[list[0]].imgUrl,
                getItemList[list[0]]
            )
        )
        cardAdapter.addCardItem(
            CardItem(
                getItemList[list[1]].title,
                getItemList[list[1]].title,
                getItemList[list[1]].imgUrl,
                getItemList[list[1]]
            )
        )
        cardAdapter.addCardItem(
            CardItem(
                getItemList[list[2]].title,
                getItemList[list[2]].title,
                getItemList[list[2]].imgUrl,
                getItemList[list[2]]
            )
        )
        cardAdapter.addCardItem(
            CardItem(
                getItemList[list[3]].title,
                getItemList[list[3]].title,
                getItemList[list[3]].imgUrl,
                getItemList[list[3]]
            )
        )

        var mLastOffset = 0f

        binding.cardViewPager.adapter = cardAdapter
        binding.cardViewPager.offscreenPageLimit = 3
        binding.cardViewPager.currentItem = 0
        binding.homeContentText.text = cardAdapter.getCardItem(0).getText() //추천 레시피 이름 표시
        val emoji = 0x1F4AD //말풍선 이모지
        val emojiText = "무엇을 먹을지 고민이라면 ${String(Character.toChars(emoji))}" //이모지 추가한 문구
        binding.homeRecommendText.text = emojiText //이모지 추가한 문구로 변경

        //뷰페이저
        binding.cardViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val realCurrentPosition: Int
                val nextPosition: Int
                val baseElevation: Float = (binding.cardViewPager.adapter as CardPagerAdapter).getBaseElevation()
                val realOffset: Float
                val goingLeft: Boolean = mLastOffset > positionOffset

                if (goingLeft) {
                    realCurrentPosition = position + 1
                    nextPosition = position
                    realOffset = 1 - positionOffset
                } else {
                    nextPosition = position + 1
                    realCurrentPosition = position
                    realOffset = positionOffset
                }

                if (nextPosition > (binding.cardViewPager.adapter as CardPagerAdapter).count - 1
                    || realCurrentPosition > (binding.cardViewPager.adapter as CardPagerAdapter).count - 1) {
                    return
                }

                val currentCard: CardView = (binding.cardViewPager.adapter as CardPagerAdapter).getCardViewAt(realCurrentPosition)

                currentCard.scaleX = (1 + 0.1 * (1 - realOffset)).toFloat()
                currentCard.scaleY = (1 + 0.1 * (1 - realOffset)).toFloat()

                currentCard.cardElevation = baseElevation + (baseElevation
                        * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset))


                val nextCard: CardView = (binding.cardViewPager.adapter as CardPagerAdapter).getCardViewAt(nextPosition)

                nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
                nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()

                nextCard.cardElevation = baseElevation + (baseElevation
                        * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * realOffset)

                mLastOffset = positionOffset
            }

            override fun onPageSelected(position: Int) {
                binding.homeContentText.text = cardAdapter.getCardItem(position).getText() //뷰페이저 페이지 변경 시 그에 따라 레시피 이름 변경
            }
        })

        binding.homeToRecipeBtn.setOnClickListener {

            if(binding.homeEditMoney.text.toString().replace(" ", "") == "")
                Log.e("search item", "값 없음")

            else{
                var searchItem = getItemList.stream().filter { item -> (item.price != "") && (item.price.toInt() < binding.homeEditMoney.text.toString().toInt())}.toList() as ArrayList<RecipeData>
                //인텐트 객체 생성
                val intent = Intent(this.context, BudgetActivity::class.java)
                intent.putExtra("itemlist", searchItem)
                startActivity(intent)
            }
        }
        binding.homeAddBtn.setOnClickListener {
            val intent = Intent(this.context, RegisterIngredientActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    private fun loadData(): ArrayList<RecipeData> {
        var itemList = ArrayList<RecipeData>()
        val assetManager = this.requireActivity().assets
        val inputStream: InputStream = assetManager.open("recipe_general.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "utf-8"))
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
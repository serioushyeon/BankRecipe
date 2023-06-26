package com.example.bankrecipe.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.databinding.ActivityRegisterIngredientBinding
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader


class RegisterIngredientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterIngredientBinding
    private var originalItemList = ArrayList<RegisterIngredientData>()
    private var searchItemList = ArrayList<RegisterIngredientData>()
    private lateinit var adapter : RegisterIngredientAdapter
    var selectItemList = ArrayList<RegisterIngredientData>()
    private lateinit var selectAdapter : RegisterIngredientAdapter
    private var deleteItemList = ArrayList<RegisterIngredientData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val flag = intent.getStringExtra("flag")

        if(flag == "ref")
            binding.regIngRegBtn.visibility = View.GONE
        else if(flag == "post")
            binding.regIngFindBtn.visibility = View.GONE

        originalItemList = loadIngData()
        originalItemList = originalItemList.distinct() as ArrayList<RegisterIngredientData>
        searchItemList.addAll(originalItemList)

        adapter = RegisterIngredientAdapter(this, originalItemList)
        adapter.setOnItemClickListener(object: RegisterIngredientAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, item : CheckedTextView) {
                item.toggle()
                if (selectItemList.contains(searchItemList[position])) {
                    selectItemList.remove(searchItemList[position])
                } else {
                    selectItemList.add(searchItemList[position])
                }
            }
        })

        adapter.notifyDataSetChanged()

        binding.regIngSearchLv.adapter = adapter
        binding.regIngSearchLv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.regIngSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼 누를 때 호출
                
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                var text = newText
                search(text)
                return true
            }
        })

        selectAdapter =  RegisterIngredientAdapter(this, selectItemList)
        selectAdapter.setOnItemClickListener(object: RegisterIngredientAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, item: CheckedTextView) {
                item.toggle()
                if (deleteItemList.contains(selectItemList[position])) {
                    deleteItemList.remove(selectItemList[position])
                } else {
                    deleteItemList.add(selectItemList[position])
                }
            }
        })
        selectAdapter.notifyDataSetChanged()

        binding.regIngSelectLv.adapter = selectAdapter
        binding.regIngSelectLv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.regIngFab.setOnClickListener {
            selectAdapter.notifyDataSetChanged()
            for(item in selectItemList) {
                originalItemList.remove(item)
                searchItemList.remove(item)
            }
            adapter.notifyDataSetChanged()
        }

        binding.regIngDeleteIngBtn.setOnClickListener {
            for(item in deleteItemList) {
                selectItemList.remove(item)
                originalItemList.add(item)
                searchItemList.add(item)
                selectAdapter.notifyDataSetChanged()
                adapter.notifyDataSetChanged()
            }
        }
        binding.regIngFindBtn.setOnClickListener {
            if(selectItemList.isEmpty())
                Toast.makeText(this, "등록된 재료가 없습니다.", Toast.LENGTH_SHORT).show()
            else
            {
                Intent(this, RecipeForIngredientActivity::class.java).apply { putExtra("item", selectItemList) }
                    .run { startActivity(this) }
            }

        }
        binding.regIngRegBtn.setOnClickListener{
            val intent = Intent(applicationContext, RecipePostRegisterActivity::class.java).apply {
                //엑티비티에서 갖고올 데이터
                putExtra("ingredient", selectItemList)
                //데이터 전달이 성공했을 때의 변수 값 저장
                // Result_ok = -1 일 때 엑티비티에 전달된다.

            }
            setResult(RESULT_OK, intent)
            //엑티비티 종료
            if (!isFinishing)
                finish()
        }
    }
    private fun search(text: String?){
        searchItemList.clear()

        if(text!!.isEmpty())
            searchItemList.addAll(originalItemList)
        else{
            for(item in originalItemList)
                if(item.ingredient.contains(text))
                    searchItemList.add(item)
        }
        adapter.setItems(searchItemList)
    }
    private fun loadIngData(): ArrayList<RegisterIngredientData> {
        var itemList = ArrayList<RegisterIngredientData>()
        val assetManager = this.assets
        val inputStream: InputStream = assetManager.open("KcalData.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream)) //UTF-8을 옵션으로 주었을 때 Strng 비교에 문제 발생, 인코딩 옵션 삭제함
        val allContent = csvReader.readAll()
        for (content in allContent) {
            itemList.add(RegisterIngredientData(content[0], false, content[7]))
        }
        return itemList
    }
}
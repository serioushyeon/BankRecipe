package com.example.bankrecipe.ui.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankrecipe.R
import com.example.bankrecipe.databinding.ActivityRecipePostRegisterBinding

class RecipePostRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipePostRegisterBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var ingredient : ArrayList<RegisterIngredientData>
    lateinit var adapter : RecipePostRegisterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipePostRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ingredient = ArrayList<RegisterIngredientData>()
        var ingStringList = ArrayList<String>()
        for(item in ingredient){
            ingStringList.add(item.ingredient)
        }
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {//Result 매개변수 콜백 메서드
                //ActivityResultLauncher<T>에서 T를 intent로 설정했으므로
                //intent자료형을 Result 매개변수(콜백)를 통해 받아온다
                //엑티비티에서 데이터를 갖고왔을 때만 실행
                if (it.resultCode == RESULT_OK) {
                    val intent = it.data
                    ingredient = intent!!.getSerializableExtra("ingredient") as ArrayList<RegisterIngredientData>
                    ingStringList.clear()
                    for(item in ingredient){
                        ingStringList.add(item.ingredient)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

        binding.recipePostRegIngBtn.setOnClickListener {
            val intent = Intent(applicationContext, RegisterIngredientActivity::class.java)
            intent.putExtra("flag", "post")
            activityResultLauncher.launch(intent)

        }
        adapter = RecipePostRegisterAdapter(this,ingStringList)
        adapter.notifyDataSetChanged()
        binding.recipePostRegIngRv.adapter = adapter
        binding.recipePostRegIngRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        binding.recipePostRegBtn.setOnClickListener {
        }

    }
}
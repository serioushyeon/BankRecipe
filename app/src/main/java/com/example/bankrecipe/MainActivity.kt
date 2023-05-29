package com.example.bankrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.ActivityMainBinding
import com.example.bankrecipe.ui.community.CommunityData
import com.example.bankrecipe.ui.map.MapActivity
import com.example.bankrecipe.ui.map.MapData
import com.example.bankrecipe.ui.recipe.RecipeIngredientPrice
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null //4.6추가
    private lateinit var binding: ActivityMainBinding
    private lateinit var uid : String
    private lateinit var mapaddress: String
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth //4.6 추가
        firestore = FirebaseFirestore.getInstance()
        supportActionBar!!.elevation = 0.0F //액션바 그림자 제거
        mapaddress = intent.getStringExtra("addresskey").toString()
        if (mapaddress!="null"){
            firestore.collection("map").document(mapaddress).get().addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    Log.d("key값",mapaddress)
                    var mapdata = task.result?.toObject(MapData::class.java)
                    supportActionBar!!.setTitle(mapdata?.mapaddress)

                }

                }
        }else {
            firestore.collection("map").document(FBAuth.getUid()).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("key값", mapaddress)
                        var mapdata = task.result?.toObject(MapData::class.java)
                        supportActionBar!!.setTitle(mapdata?.mapaddress)
                    }
                }
        }

        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null;
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_recipe, R.id.navigation_community, R.id.navigation_chat, R.id.navigation_myPage
            )
        )
        navView.setupWithNavController(navController)
        RecipeIngredientPrice.updatePriceItem()
        uid = intent.getStringExtra("userUid").toString() //uid저장. 로그인 없이 진행될경우 null값
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu) //상단바 메뉴
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){ //상단바 메뉴 이벤트
            R.id.main_action_location -> {
                Toast.makeText(applicationContext,"자신의 위치 설정"
                ,Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)}
            R.id.main_action_btn1 -> { return true }
            R.id.main_action_btn2 -> { return true }
            R.id.main_action_btn3 -> { return  true}
            else -> {return super.onOptionsItemSelected(item)}



        }
    }
}
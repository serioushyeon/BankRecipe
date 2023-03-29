package com.example.bankrecipe

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bankrecipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.elevation = 0.0F //액션바 그림자 제거

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
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu) //상단바 메뉴
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){ //상단바 메뉴 이벤트
            R.id.main_action_btn1 -> { return true }
            R.id.main_action_btn2 -> { return true }
            R.id.main_action_btn3 -> { return  true}
            else -> {return super.onOptionsItemSelected(item)}



        }
    }
}
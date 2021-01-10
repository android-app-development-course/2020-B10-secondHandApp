package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.ui.activity.LoginActivity
import com.example.myapplication.ui.activity.PublishTestActivity
import com.example.myapplication.ui.activity.SearchResultActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.URI


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ws=JWebSocketClient(URI("ws://echo.websocket.org"))
        Thread{
            try {
                ws.connectBlocking()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }.start()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        button2.setOnClickListener(){
            val intent=Intent(this,
                PublishTestActivity::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener(){
            val data=searchView.query.toString()
            val intent=Intent(this,
                SearchResultActivity::class.java)
            intent.putExtra("words",data)
            startActivity(intent)
        }

        temp_loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
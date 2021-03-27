package com.example.userretrofit.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.userretrofit.R
import com.example.userretrofit.Variables
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.databinding.ActivityMainBinding
import com.example.userretrofit.network.CreateRetrofit
import com.example.userretrofit.network.NetworkInterceptor
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val interceptor = NetworkInterceptor(this)
        val restApi = CreateRetrofit.getRetrofit(interceptor).create(RestApi::class.java)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        Variables.isNetworkAvailable.observe(this){ result->

           if (result==true){
               Snackbar.make(findViewById(android.R.id.content),
                    "Tarmoq tiklandi", Snackbar.LENGTH_LONG
                ).show()
           } else{
               Snackbar.make(findViewById(android.R.id.content),
                    "Tarmoqqa ulanmagan", Snackbar.LENGTH_INDEFINITE
                ).show()
           }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userFragment, R.id.postsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}
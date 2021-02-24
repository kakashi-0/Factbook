package com.gmail.kuldeepsinghchilwal.factbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.kuldeepsinghchilwal.factbook.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.doubleclick.PublisherAdRequest.Builder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
        MobileAds.initialize(this) {}
        val adRequest = Builder().build()
        binding.publisherAdView.loadAd(adRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

}
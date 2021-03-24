package com.gmail.kuldeepsinghchilwal.factbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.kuldeepsinghchilwal.factbook.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.doubleclick.PublisherAdRequest.Builder
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        MobileAds.initialize(this) {}
        val bannerAdRequest = Builder().build()
        binding.publisherAdView.loadAd(bannerAdRequest)


    }
    //navigation menu and nav bar back button
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

}
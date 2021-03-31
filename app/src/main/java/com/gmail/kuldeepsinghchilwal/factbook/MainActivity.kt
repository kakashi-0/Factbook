package com.gmail.kuldeepsinghchilwal.factbook

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    private var mAdManagerInterstitialAd: AdManagerInterstitialAd? = null

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

        //interstitial ad
        var adRequest = AdManagerAdRequest.Builder().build()

        AdManagerInterstitialAd.load(
            this,
            getString(R.string.test_interstitial_ad),
            adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mAdManagerInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    mAdManagerInterstitialAd = interstitialAd
                }
            })
        mAdManagerInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                mAdManagerInterstitialAd = null;
            }
        }

    }
    //navigation menu and nav bar back button
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.rateUs -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
                true
            }
            R.id.interstitialAd -> {
                    if (mAdManagerInterstitialAd != null) {
                        mAdManagerInterstitialAd?.show(this)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        Toast.makeText(this, "Loading Ad please visit after a minute ", Toast.LENGTH_SHORT).show()                    }
                    mAdManagerInterstitialAd=null
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
package com.gmail.kuldeepsinghchilwal.factbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gmail.kuldeepsinghchilwal.factbook.databinding.ActivityInterstitialAdBinding
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback

class InterstitialAd : AppCompatActivity() {
    lateinit var binding: ActivityInterstitialAdBinding
    //Ad
    private var mAdManagerInterstitialAd: AdManagerInterstitialAd? = null
    private var TAG = "Interstitial"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interstitial_ad)
        //ad
        var InterstitialAdRequest = AdManagerAdRequest.Builder().build()


            AdManagerInterstitialAd.load(
                this,
                "/6499/example/interstitial",
                InterstitialAdRequest,
                object : AdManagerInterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.message)
                        mAdManagerInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mAdManagerInterstitialAd = interstitialAd
                    }
                })
            var adRequest = AdManagerAdRequest.Builder().build()

            AdManagerInterstitialAd.load(
                this,
                "/6499/example/interstitial",
                adRequest,
                object : AdManagerInterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.message)
                        mAdManagerInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mAdManagerInterstitialAd = interstitialAd
                    }
                })
            binding.watchAD.setOnClickListener {
                    if (mAdManagerInterstitialAd != null) {
                        mAdManagerInterstitialAd?.show(this)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        Toast.makeText(this, "Loading Ad please visit after a minute ", Toast.LENGTH_SHORT).show()                    }
            mAdManagerInterstitialAd=null
            }
            }
    }

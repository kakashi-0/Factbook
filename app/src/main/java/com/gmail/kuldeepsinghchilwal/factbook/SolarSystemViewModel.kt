package com.gmail.kuldeepsinghchilwal.factbook

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SolarSystemViewModel : ViewModel() {
    //generating new resource id from idGenerator method
    var solarSystemResourceId = 0

    init {
        Log.i("solarSystemFactViewModel","View Model Created.")
    }


    fun randomSolarSystemIdGenerator (): String {
        // generating random number to get random facts . like solarsystem1,solarsystem2 etc
        val factInCategory = Random.nextInt(1, 11)
        //returning int type resource id
        return "solarsystem$factInCategory"
    }}
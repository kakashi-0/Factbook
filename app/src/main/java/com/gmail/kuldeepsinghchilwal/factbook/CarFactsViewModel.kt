package com.gmail.kuldeepsinghchilwal.factbook

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CarFactsViewModel : ViewModel() {
    //generating new resource id from idGenerator method
    var carResourceId = 0
    //creating mutable list to store and retrieve resource ids in same order as these are created. We are not storing anything on index 0
    val resIdList = mutableListOf<Int>()
    //index number for resIdList mutable list
    var ind=0

    init {
        Log.i("carFactViewModel","View Model Created.")
    }


    fun randomCarIdGenerator (): String {
        // generating random number to get random facts . like car1,car2 etc
        val factInCategory = Random.nextInt(1, 11)
        //returning int type resource id
        return "car$factInCategory"
    }}
package com.gmail.kuldeepsinghchilwal.factbook

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class HomeFragmentViewModel : ViewModel() {
    //generating new resource id from idGenerator method
    var resourceId = 0
    //var holding category name for creating resource id
    var category=""
    //creating mutable list to store and retrieve resource ids in same order as these are created. We are not storing anything on index 0
    val resIdList = mutableListOf<Int>(0,)
    //index number for resIdList mutable list
    var ind=0

    init {
        Log.i("HomeFragmentViewModel","View Model Created.")
    }


    fun randomIdGenerator (): String {
            val categoryArray = arrayOf<String>("car", "solarsystem","cat","dog","smartphone",
            "human","insect","spaceExplore","shoes","motorcycle")
            val randomCategoryArrayIndex = Random.nextInt(10)
            //getting random categories
            category  = categoryArray[randomCategoryArrayIndex]
        // generating random number to get random facts . like car1,car2 etc
        val factInCategory = Random.nextInt(1, 21)
        //returning int type resource id
        return category+factInCategory
    }
}
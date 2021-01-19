package com.gmail.kuldeepsinghchilwal.factbook

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class HomeFragmentViewModel : ViewModel() {
    //generating new resource id from idGenerator method
    var resourceId = 0

    init {
        Log.i("HomeFragmentViewModel","View Model Created.")
    }


    fun idGenerator (): String {
        val categoryArray = arrayOf<String>("car", "solarsystem")
        val randomCategoryArrayIndex = Random.nextInt(2)
        //getting random categories
        val category  = categoryArray[randomCategoryArrayIndex]
        // generating random number to get random facts . like car1,car2 etc
        val factInCategory = Random.nextInt(1, 11)
        //returning int type resource id
        return category+factInCategory
    }
}
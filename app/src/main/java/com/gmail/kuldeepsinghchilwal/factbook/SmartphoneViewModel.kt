package com.gmail.kuldeepsinghchilwal.factbook

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SmartphoneViewModel : ViewModel() {
    //generating new resource id from idGenerator method
    var smartphoneResourceId = 0
    //list to stored shuffled list
    var shuffledList = listOf<Int>()
    //index keeper for shuffled list
    var shuffleInd = 0

}
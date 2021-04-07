package com.gmail.kuldeepsinghchilwal.factbook.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.*
import java.lang.reflect.Array.get
import kotlin.random.Random

 class FragmentViewModel(private val app: Application) : AndroidViewModel(app) {
    private val resourceIdLocal = MutableLiveData<Int>()
    //generating new resource id from idGenerator method
    val resourceId : LiveData<Int>
        get() = resourceIdLocal
    //list to stored shuffled list
    var shuffledList = listOf<Int>()
    //index keeper for shuffled list
    private var shuffleInd = 0
    // number of facts available in each category
     var lastIndex = 0
    //category Name
     var category= ""

    init {
        resourceIdLocal.value = 0
        Log.i("viewModel Fragment","initial : $lastIndex")
    }

     //resetting resourceIdLocal every time new fact category is opened
     fun firstResourceId() :Int{
         Log.i("viewModel Fragment","updated : $lastIndex")
         return app.resources.getIdentifier(idGenerator(),"string",app.packageName)
     }

    fun nextFact(){
        //index of shuffled list -> shuffleInd <= n-2
        if (shuffleInd <=lastIndex-2 ) {
            shuffleInd++
            resourceIdLocal.value = app.resources.getIdentifier(idGenerator(), "string", app.packageName)
        }
        //if its the last id (n-1)
        if (shuffleInd==lastIndex-1 ){
            Toast.makeText(app, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
        }
    }

    fun previousFact(){
        if (shuffleInd >= 1 ) {
            //getting previous index then current one
            shuffleInd--
            resourceIdLocal.value = app.resources.getIdentifier(idGenerator(),"string",app.packageName)
        }

        else
        {
            Toast.makeText(app, "No previous fact to display!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getShareString() :String{
        return app.getString(resourceIdLocal.value?:0) + "\n *\n *\n *\nFor more amazing facts Download FactBook:"+ "https://play.google.com/store/apps/details?id=com.gmail.kuldeepsinghchilwal.factbook"
    }

    /**
     * This function is used to generate ids to fetch facts from string file
     */
    private fun idGenerator ( ): String {
//         generating random number to get random facts . like car1,car2 etc
//        returning int type resource id
        return "$category${shuffledList[shuffleInd]}"
    }

 }
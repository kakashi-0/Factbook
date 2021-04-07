package com.gmail.kuldeepsinghchilwal.factbook.viewmodels

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.kuldeepsinghchilwal.factbook.notification.AlarmReceiver
import java.util.*
import kotlin.random.Random

class HomeFragmentViewModel(private val app: Application) : AndroidViewModel(app) {
    private val notifyIntent = Intent(app, AlarmReceiver::class.java)

    private val resourceIdLocal = MutableLiveData<Int>()

    val resourceId: LiveData<Int>
        get()= resourceIdLocal

    //var holding category name for creating resource id
    private var category=""
    //creating mutable list to store and retrieve resource ids in same order as these are created. We are not storing anything on index 0
    private val resIdList = mutableListOf<Int>(0,)
    //index number for resIdList mutable list
     private var ind=0

    init {
        //getting instance of AlarmManager
        val alarmMgr = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //initializing variables
        resourceIdLocal.value  = 0

        //defining PendingIntent
        val notifyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            app,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Set the alarm to start at approximately 11:15 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE,15)
        }

        if (System.currentTimeMillis() <= calendar.timeInMillis) {

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                notifyPendingIntent
            )
        }
    }

    fun randomIdGenerator () {
            val categoryArray = arrayOf<String>("car", "solarsystem","cat","dog","smartphone",
            "human","insect","spaceExplore","shoes","motorcycle")
            val randomCategoryArrayIndex = Random.nextInt(10)
            //getting random categories
            category  = categoryArray[randomCategoryArrayIndex]
        // generating random number to get random facts . like car1,car2 etc
        val factInCategory = Random.nextInt(1, 21)
        //setting int type resource id to live data
        resourceIdLocal.value = app.resources.getIdentifier(
            category+factInCategory,
            "string",
            app.packageName
        )
    }

    fun updatingFactsList(){
        //incrementing list index
        ind++
        //adding the element created to mutable list
        resIdList.add(ind, resourceIdLocal.value?:0)
    }

    fun showingPrevFact(){
        if (ind >=2) {
            //getting previous index then current one
            ind--
            //saving resource id for viewModel
            resourceIdLocal.value = resIdList[ind]
        }
        else{
            Toast.makeText(app, "No previous fact to display!", Toast.LENGTH_SHORT).show()
        }
    }

    fun stringToBeShared() : String{
        return app.getString(resIdList[ind]) + "\n \n *\n *\n *\nFor more amazing facts Download FactBook:"+ "https://play.google.com/store/apps/details?id=com.gmail.kuldeepsinghchilwal.factbook"
    }

}
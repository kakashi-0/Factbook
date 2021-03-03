package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentCarBinding

class Car : Fragment() {

    private lateinit var viewModel: FragmentViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.car1, R.string.car2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //binding initialized
        val dataBindingUtil: FragmentCarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_car, container, false)
        //viewModel initialized
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        //resource id that will be generated after each click of buttons
        var carResID = viewModel.resourceId
        //setting text if fragment was recreated due to screen rotation or other factors
        if (carResID != 0) {
            dataBindingUtil.carFactTextview.setText(carResID)
        }
        //if fragment is being created for the first time. We generate new id to avoid blank screen
        else {
//shuffling the list
            viewModel.shuffledList = numberOfFacts.shuffled()
            carResID = resources.getIdentifier(carIdGenerator(), "string", context?.packageName)
            dataBindingUtil.carFactTextview.setText(carResID)

        }
        //setting onClick listener on next button
        dataBindingUtil.carNext.setOnClickListener {
            if (viewModel.shuffleInd <=8 ) {
                viewModel.shuffleInd++
                carResID = resources.getIdentifier(carIdGenerator(), "string", context?.packageName)
            }
            //if its the last id
            if (viewModel.shuffleInd==9 ){
                Toast.makeText(this.context, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
            }
            dataBindingUtil.carFactTextview.setText(carResID)
        }

        //setting onclick listener on previous button
        dataBindingUtil.carPrevious.setOnClickListener {
            if (viewModel.shuffleInd >= 1 ) {
                //getting previous index then current one
                viewModel.shuffleInd--
                Log.i("car previous","shuffle Ind = ${viewModel.shuffleInd}")
                carResID = resources.getIdentifier(carIdGenerator(),"string",context?.packageName)
                dataBindingUtil.carFactTextview.setText(carResID)
            }

                else
             {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

//setting onclick listener on share button
        dataBindingUtil.carShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(carResID)))
        }
        return dataBindingUtil.root
    }

    /**
     * This function is used to generate ids to fetch facts from string file
     */
    private fun carIdGenerator ( ): String {
            // generating random number to get random facts . like car1,car2 etc
            val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]
           //returning int type resource id
            return "car$factInCategory"
    }
}
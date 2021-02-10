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
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentFactsCarBinding
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import kotlin.random.Random

class CarFactsFragment : Fragment() {

    private lateinit var viewModel: CarFactsViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.car1, R.string.car2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //binding initialized
        val dataBindingUtil: FragmentFactsCarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts_car, container, false)
        //viewModel initialized
        viewModel = ViewModelProvider(this).get(CarFactsViewModel::class.java)
        //resource id that will be generated after each click of buttons
        var carResID = viewModel.carResourceId
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
            //incrementing shuffle index when its 1 lower than last id and incremenentInd is true
            if (viewModel.shuffleInd <=8 ) {
                viewModel.shuffleInd++
                Log.i("car fact id generator", "shuffled list ${viewModel.shuffleInd}")

            }
            carResID = resources.getIdentifier(carIdGenerator(), "string", context?.packageName)
            dataBindingUtil.carFactTextview.setText(carResID)
            Log.i("car next finished","shuffle Ind = ${viewModel.shuffleInd}")
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
    fun carIdGenerator ( ): String {
            // generating random number to get random facts . like car1,car2 etc
            val factInCategory = viewModel.shuffledList[viewModel.shuffleInd]

        //if its the last id
        if (viewModel.shuffleInd==9 ){
            Toast.makeText(this.context, "CONGRATULATIONS! you have read all facts in our data!", Toast.LENGTH_SHORT).show()
        }
            //returning int type resource id
            return "car$factInCategory"
    }
}
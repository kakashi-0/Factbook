package com.gmail.kuldeepsinghchilwal.factbook.factFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.kuldeepsinghchilwal.factbook.MainActivity
import com.gmail.kuldeepsinghchilwal.factbook.viewmodels.FragmentViewModel
import com.gmail.kuldeepsinghchilwal.factbook.R
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentCarBinding

class Car : Fragment() {

    private lateinit var viewModel: FragmentViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.car1, R.string.car2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //binding initialized
        val dataBindingUtil: FragmentCarBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_car, container, false)

        //viewModel initialized
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)

        viewModel.category = "car"


        // setting up LiveData observation relationship and updating fact
        viewModel.resourceId.observe(viewLifecycleOwner, { acquiredResourceId ->
            if(acquiredResourceId !=0){
                dataBindingUtil.carFactTextview.setText(acquiredResourceId)
            }
            else{
                //shuffling numberOfFacts List
                viewModel.shuffledList = numberOfFacts.shuffled()
                //setting last index number in ViewModel
                viewModel.lastIndex = numberOfFacts.size
                dataBindingUtil.carFactTextview.setText(viewModel.firstResourceId())
            }
        })

        //setting onClick listener on next button
        dataBindingUtil.carNext.setOnClickListener {
            viewModel.nextFact()
        }

        //setting onclick listener on previous button
        dataBindingUtil.carPrevious.setOnClickListener {
            viewModel.previousFact()
        }

//setting onclick listener on share button
        dataBindingUtil.carShare.setOnClickListener {
            val shareString = viewModel.getShareString()
                //declaring our intent action
                startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, shareString))

        }
        return dataBindingUtil.root
    }

}
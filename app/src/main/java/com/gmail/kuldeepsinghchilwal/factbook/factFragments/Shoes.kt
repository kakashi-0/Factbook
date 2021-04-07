package com.gmail.kuldeepsinghchilwal.factbook.factFragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gmail.kuldeepsinghchilwal.factbook.viewmodels.FragmentViewModel
import com.gmail.kuldeepsinghchilwal.factbook.R
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentShoesBinding

class Shoes : Fragment() {
    private lateinit var binding: FragmentShoesBinding
    private lateinit var viewModel: FragmentViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.shoes1, R.string.shoes2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        viewModel.category = "shoes"


        // initializing property binding
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_shoes,container,false)

        // setting up LiveData observation relationship and updating fact
        viewModel.resourceId.observe(viewLifecycleOwner, { acquiredResourceId ->
            if(acquiredResourceId !=0){
                binding.shoesTextview.setText(acquiredResourceId)
            }
            else{
                //shuffling numberOfFacts List
                viewModel.shuffledList = numberOfFacts.shuffled()
                //setting last index number in ViewModel
                viewModel.lastIndex = numberOfFacts.size
                binding.shoesTextview.setText(viewModel.firstResourceId())

            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setting onclick listener for  next buton
        binding.shoesNext.setOnClickListener {
          viewModel.nextFact()
        }

        //setting onclick listener on previous button
        binding.shoesPrevious.setOnClickListener {
          viewModel.previousFact()
        }

        //setting onclick listener for send button
        binding.shoesShare.setOnClickListener {
            val shareString = viewModel.getShareString()
            //declaring our intent action
                startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, shareString))
                }
    }
}
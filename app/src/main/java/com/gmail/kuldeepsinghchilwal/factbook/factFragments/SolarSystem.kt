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
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentSolarSystemBinding

class SolarSystem : Fragment() {
    private lateinit var binding: FragmentSolarSystemBinding
    private lateinit var viewModel: FragmentViewModel
    //numbers used to determine which fact to fetch. Ex. we have R.string.solarsystem1, R.string.solarsystem2 etc. So we determine the last digit using this list
    private  val numberOfFacts = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //initializing viewModel
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        // initializing property binding
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_solar_system,container,false)


        viewModel.category = "solarsystem"

        // setting up LiveData observation relationship and updating fact
        viewModel.resourceId.observe(viewLifecycleOwner, { acquiredResourceId ->
            if(acquiredResourceId !=0){
                binding.solarSysTextview.setText(acquiredResourceId)
            }
            else{
                //shuffling numberOfFacts List
                viewModel.shuffledList = numberOfFacts.shuffled()
                //setting last index number in ViewModel
                viewModel.lastIndex = numberOfFacts.size
                binding.solarSysTextview.setText(viewModel.firstResourceId())

            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setting onclick listener for  next buton
        binding.solarSysNext.setOnClickListener {
            viewModel.nextFact()
        }

        //setting onclick listener on previous button
        binding.solarSysPrevious.setOnClickListener {
            viewModel.previousFact()
        }

        //setting onclick listener for send button
        binding.solarSysShare.setOnClickListener {
            val shareString = viewModel.getShareString()
            //declaring our intent action
                startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, shareString))
                  }
    }
}
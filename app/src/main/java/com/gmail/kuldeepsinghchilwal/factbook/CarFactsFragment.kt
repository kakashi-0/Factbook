package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentFactsCarBinding
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentHomeBinding

class CarFactsFragment : Fragment() {

    private lateinit var viewModel: CarFactsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dataBindingUtil :FragmentFactsCarBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_facts_car,container,false)
        viewModel = ViewModelProviders.of(this).get(CarFactsViewModel::class.java)
        //resource id that will be generated after each click of buttons
        var carResID = viewModel.carResourceId
        //setting text if fragment was recreated due to screen rotation or other factors
        if (carResID != 0) {
            dataBindingUtil.carFactTextview.setText(carResID)
        }
        dataBindingUtil.carNext.setOnClickListener{
            carResID = resources.getIdentifier(viewModel.randomCarIdGenerator(),"string",context?.packageName)
            viewModel.carResourceId = carResID
            dataBindingUtil.carFactTextview.setText(carResID)
        }

        dataBindingUtil.carShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,getString(carResID)))
        }
        return dataBindingUtil.root
    }

}
package com.gmail.kuldeepsinghchilwal.factbook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.kuldeepsinghchilwal.factbook.databinding.FragmentFactsCarBinding

class CarFactsFragment : Fragment() {

    private lateinit var viewModel: CarFactsViewModel

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
            carResID = resources.getIdentifier(viewModel.randomCarIdGenerator(), "string", context?.packageName)
            //adding first created resource in 0th index
            viewModel.resIdList.add(viewModel.ind, carResID)
            dataBindingUtil.carFactTextview.setText(carResID)
        }
        //setting onClick listener on next button
        dataBindingUtil.carNext.setOnClickListener {

            carResID = resources.getIdentifier(viewModel.randomCarIdGenerator(), "string", context?.packageName)
            viewModel.carResourceId = carResID
            //incrementing list index
            viewModel.ind++         //ind=1
            //adding the element created to mutable list
            viewModel.resIdList.add(viewModel.ind, carResID)
            dataBindingUtil.carFactTextview.setText(carResID)
        }

        //setting onclick listener on previous button
        dataBindingUtil.carPrevious.setOnClickListener {
            if (viewModel.ind >= 1) {
                //getting previous index then current one
                viewModel.ind--
                dataBindingUtil.carFactTextview.setText(viewModel.resIdList[viewModel.ind])
            } else {
                Toast.makeText(this.context, "No previous fact to display!", Toast.LENGTH_SHORT).show()
            }
        }

//setting onclick listener on share button
        dataBindingUtil.carShare.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(viewModel.resIdList[viewModel.ind])))
        }
        return dataBindingUtil.root
    }

}
package com.gmail.kuldeepsinghchilwal.factbook

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CarFactsFragment : Fragment() {

    companion object {
        fun newInstance() = CarFactsFragment()
    }

    private lateinit var viewModel: CarFactsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_facts_car, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarFactsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}